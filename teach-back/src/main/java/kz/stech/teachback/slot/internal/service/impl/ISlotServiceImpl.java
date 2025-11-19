package kz.stech.teachback.slot.internal.service.impl;

import kz.stech.teachback.shared.dto.EnableSlotDto;
import kz.stech.teachback.shared.dto.SlotDto;
import kz.stech.teachback.slot.internal.model.EnableSlot;
import kz.stech.teachback.slot.internal.model.Slot;
import kz.stech.teachback.slot.internal.repository.EnableSlotRepository;
import kz.stech.teachback.slot.internal.repository.SlotRepository;
import kz.stech.teachback.slot.internal.service.ICreateSlots;
import kz.stech.teachback.slot.internal.service.ISlotService;
import kz.stech.teachback.slot.internal.service.OpenAccessToSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ISlotServiceImpl implements ISlotService, ICreateSlots, OpenAccessToSlot {

    private final SlotRepository slotRepository;
    private final EnableSlotRepository enableSlotRepository;

    @Override
    public SlotDto getSlotByTeacherId(UUID teacherId) {
        return slotRepository.findByTeacherId(teacherId).map(s -> new SlotDto(s.getId(), s.getTeacherId(), s.getTotalSlots(), s.getAvailableSlots()))
                .orElseThrow(() -> new RuntimeException("Slot not found")
        );
    }

    @Override
    public void createSlots(UUID teacherId, Long totalSlots) {


        if(slotRepository.existsByTeacherId(teacherId)) return;

        Slot slot = new Slot();
        slot.setTeacherId(teacherId);
        slot.setTotalSlots(totalSlots);
        slot.setAvailableSlots(totalSlots);

        slotRepository.save(slot);
    }

    @Override
    @Transactional
    public Object execute(UUID teacherId) {

        Slot slot = slotRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new RuntimeException("slots with teacher ID " + teacherId + " not found"));

        if(slot.getAvailableSlots() == 0) {
            throw new RuntimeException("not available slots");
        }

        EnableSlot enableSlot = new EnableSlot();
        enableSlot.setSlot(slot);
        enableSlot.setSlotEnabledDateTime(LocalDateTime.now());
        enableSlot.setIsActive(true);
        enableSlot.setActivationCode(String.valueOf(UUID.randomUUID()));

        enableSlotRepository.save(enableSlot);

        return new EnableSlotDto(enableSlot.getId(), enableSlot.getSlot().getId(), enableSlot.getActivationCode(), enableSlot.getIsActive(), enableSlot.getSlot().getTeacherId());
    }
}
