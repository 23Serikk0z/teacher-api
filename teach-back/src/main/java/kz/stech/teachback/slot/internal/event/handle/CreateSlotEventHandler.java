package kz.stech.teachback.slot.internal.event.handle;


import kz.stech.teachback.shared.dto.event.TeacherAccountCreatedEvent;
import kz.stech.teachback.slot.internal.service.ICreateSlots;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSlotEventHandler {

    private final ICreateSlots createSlots;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAddSlotEvent(TeacherAccountCreatedEvent event) {
        createSlots.createSlots(event.teacherId(), 5L);
    }

}
