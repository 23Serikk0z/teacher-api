package kz.stech.teachback.slot.internal.event.handle;


import kz.stech.teachback.shared.dto.event.SubscribeToSlotEvent;
import kz.stech.teachback.slot.internal.repository.EnableSlotRepository;
import kz.stech.teachback.slot.internal.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class SubscribeToSlotEventHandle {

    private final EnableSlotRepository enableSlotRepository;
    private final SlotRepository slotRepository;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(SubscribeToSlotEvent event) {

        enableSlotRepository.findById(event.enableSlotId())
                .ifPresent(enableSlot -> {

                    if(!enableSlot.getIsActive()) {
                        throw new RuntimeException("slot already inactive");
                    }
                    enableSlot.setSlotDisabledDateTime(LocalDateTime.now());
                    enableSlot.setIsActive(false);
                });

        slotRepository.findById(event.slotId()).ifPresent(slot -> {
            if(slot.getAvailableSlots() == 0) {
                throw new RuntimeException("not available slot");
            }
            slot.setAvailableSlots(slot.getAvailableSlots() - 1L);
        });

    }
}
