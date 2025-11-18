package kz.stech.teachback.slot.internal.service.impl;

import kz.stech.teachback.shared.dto.SlotDto;
import kz.stech.teachback.slot.internal.model.Slot;
import kz.stech.teachback.slot.internal.repository.SlotRepository;
import kz.stech.teachback.slot.internal.service.ICreateSlots;
import kz.stech.teachback.slot.internal.service.ISlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ISlotServiceImpl implements ISlotService, ICreateSlots {

    private final SlotRepository slotRepository;

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
}
