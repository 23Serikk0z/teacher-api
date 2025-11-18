package kz.stech.teachback.slot.internal.service;

import kz.stech.teachback.shared.dto.SlotDto;

import java.util.UUID;

public interface ISlotService {
    SlotDto getSlotByTeacherId(UUID teacherId);


}
