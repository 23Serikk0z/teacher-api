package kz.stech.teachback.shared.dto;

import java.util.UUID;

public record EnableSlotDto
        (
                UUID id,
                UUID slotId,
                String activationCode,
                Boolean isActive,
                UUID teacherId
        ) { }
