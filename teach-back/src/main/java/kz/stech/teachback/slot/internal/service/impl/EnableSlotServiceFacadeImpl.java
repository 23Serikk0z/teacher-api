package kz.stech.teachback.slot.internal.service.impl;

import kz.stech.teachback.shared.dto.EnableSlotDto;
import kz.stech.teachback.slot.api.EnabledSlotFacade;
import kz.stech.teachback.slot.internal.repository.EnableSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EnableSlotServiceFacadeImpl implements EnabledSlotFacade {

    private final EnableSlotRepository enableSlotRepository;

    @Override
    public EnableSlotDto getEnableSlotById(UUID enableSlotId) {

        try {
            return enableSlotRepository.findById(enableSlotId)
                    .map(enableSlot -> new EnableSlotDto(enableSlot.getId(), enableSlot.getSlot().getId(), enableSlot.getActivationCode(), enableSlot.getIsActive(), enableSlot.getSlot().getTeacherId()))
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
