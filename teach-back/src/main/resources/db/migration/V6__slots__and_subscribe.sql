CREATE TABLE enabled_slots
(
    id                      UUID NOT NULL DEFAULT gen_random_uuid(),
    slot_id                 UUID,
    activation_code         VARCHAR(255),
    is_active               BOOLEAN,
    slot_enabled_date_time  TIMESTAMP WITHOUT TIME ZONE,
    slot_disabled_date_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_enabled_slots PRIMARY KEY (id)
);

CREATE TABLE subscriptions
(
    id                UUID NOT NULL DEFAULT gen_random_uuid(),
    teacher_id        UUID NOT NULL,
    student_id        UUID NOT NULL,
    enable_slot_id    UUID NOT NULL,
    subscription_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_subscriptions PRIMARY KEY (id)
);

ALTER TABLE subscriptions
    ADD CONSTRAINT uc_subscriptions_enableslotid UNIQUE (enable_slot_id);

ALTER TABLE enabled_slots
    ADD CONSTRAINT FK_ENABLED_SLOTS_ON_SLOT FOREIGN KEY (slot_id) REFERENCES slots (id);