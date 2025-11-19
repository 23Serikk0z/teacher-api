package kz.stech.teachback.slot.api;

import kz.stech.teachback.shared.dto.EnableSlotDto;

import java.util.UUID;

public interface EnabledSlotFacade {

    EnableSlotDto getEnableSlotById(UUID enableSlotId);
}
