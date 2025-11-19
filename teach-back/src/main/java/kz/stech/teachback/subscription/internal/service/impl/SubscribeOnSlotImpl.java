package kz.stech.teachback.subscription.internal.service.impl;

import kz.stech.teachback.shared.dto.EnableSlotDto;
import kz.stech.teachback.shared.dto.event.SubscribeToSlotEvent;
import kz.stech.teachback.slot.api.EnabledSlotFacade;
import kz.stech.teachback.subscription.internal.model.Subscription;
import kz.stech.teachback.subscription.internal.repository.SubscriptionRepository;
import kz.stech.teachback.subscription.internal.service.SubscribeOnSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeOnSlotImpl implements SubscribeOnSlot {


    private final EnabledSlotFacade enabledSlotFacade;
    private final SubscriptionRepository subscriptionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void execute(UUID enableSlotId,
                        UUID studentId,
                        String activationCode
    ) {
        EnableSlotDto slot = Optional.ofNullable(enabledSlotFacade.getEnableSlotById(enableSlotId))
                .orElseThrow(
                        () -> new RuntimeException("Slot with id " + enableSlotId + " not found")
                );

        if(!slot.isActive()) {
            throw new RuntimeException("Slot with id " + enableSlotId + " is not active");
        }

        if(!slot.activationCode().equals(activationCode)) {
            throw new RuntimeException("incorrect activation code");
        }

        Subscription subscription = new Subscription();
        subscription.setStudentId(studentId);
        subscription.setTeacherId(slot.teacherId());
        subscription.setSubscriptionDate(LocalDateTime.now());
        subscription.setEnableSlotId(enableSlotId);

        subscriptionRepository.save(subscription);

        applicationEventPublisher.publishEvent(new SubscribeToSlotEvent(slot.slotId(), enableSlotId));
    }
}
