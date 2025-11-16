package kz.ser.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KeycloakKafkaEventListenerProvider implements EventListenerProvider {

    private static final String TOPIC = "keycloak-user-sync";

    private final KafkaProducer<String, String> producer;
    private final ObjectMapper mapper = new ObjectMapper();

    public KeycloakKafkaEventListenerProvider() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:29092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        props.put("retries", Integer.MAX_VALUE);
        props.put("enable.idempotence", true);
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void onEvent(Event event) {
        Map<String, Object> details = event.getDetails() != null ? new HashMap<>(event.getDetails()) : null;

        if (event.getType() == EventType.REGISTER) {
            sendMessage("CREATE", event.getUserId(), event.getRealmId(), null, event.getTime(), details);
        }

        if (event.getType() == EventType.UPDATE_PROFILE ||
            event.getType() == EventType.UPDATE_EMAIL ||
            event.getType() == EventType.UPDATE_PASSWORD) {
            sendMessage("UPDATE", event.getUserId(), event.getRealmId(), null, event.getTime(), details);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        if (adminEvent.getResourceType() != ResourceType.USER) return;

        OperationType op = adminEvent.getOperationType();
        if (op != OperationType.CREATE && op != OperationType.UPDATE && op != OperationType.DELETE) return;

        String userId = extractUserId(adminEvent.getResourcePath());
        if (userId == null) return;

        String actorId = adminEvent.getAuthDetails() != null ? adminEvent.getAuthDetails().getUserId() : null;

        Map<String, Object> extra = null;
        if (includeRepresentation && (op == OperationType.CREATE || op == OperationType.UPDATE)) {
            extra = Map.of("representation", adminEvent.getRepresentation());
        }

        sendMessage(op.toString(), userId, adminEvent.getRealmId(), actorId, adminEvent.getTime(), extra);
    }

    private void sendMessage(String operation, String userId, String realm, String actorId, long timestamp, Map<String, Object> extra) {
        try {
            String username = null;
            String firstName = null;
            String lastName = null;

            if (extra != null) {
                // 1. Из деталей (саморегистрация и UPDATE_PROFILE)
                if (extra.containsKey("username")) username = (String) extra.get("username");
                if (extra.containsKey("first_name")) firstName = (String) extra.get("first_name");
                if (extra.containsKey("last_name")) lastName = (String) extra.get("last_name");

                // 2. Из representation (админ CREATE/UPDATE)
                if (extra.containsKey("representation")) {
                    String rep = (String) extra.get("representation");
                    Map<String, Object> userMap = mapper.readValue(rep, Map.class);
                    username = (String) userMap.get("username");
                    firstName = (String) userMap.get("firstName");
                    lastName = (String) userMap.get("lastName");
                }
            }

            // Главный фильтр: не шлём CREATE без имени (это пустое создание админом)
            if ("CREATE".equals(operation) && (username == null || username.isBlank())) {
                return; // ждём UPDATE, он придёт через секунду с данными
            }

            Map<String, Object> msg = new HashMap<>();
            msg.put("operation", operation);
            msg.put("userId", userId);
            msg.put("realm", realm);
            msg.put("actorId", actorId);
            msg.put("timestamp", timestamp);
            msg.put("username", username);
            msg.put("firstName", firstName);
            msg.put("lastName", lastName);

            String json = mapper.writeValueAsString(msg);
            producer.send(new ProducerRecord<>(TOPIC, userId, json));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractUserId(String resourcePath) {
        if (resourcePath == null || !resourcePath.startsWith("users/")) return null;
        return resourcePath.substring("users/".length());
    }

    @Override
    public void close() {
        producer.flush();
        producer.close();
    }
}