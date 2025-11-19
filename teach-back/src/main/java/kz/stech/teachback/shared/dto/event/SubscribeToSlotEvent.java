package kz.stech.teachback.shared.dto.event;

import java.util.UUID;

public record SubscribeToSlotEvent(UUID slotId, UUID enableSlotId) {
}
