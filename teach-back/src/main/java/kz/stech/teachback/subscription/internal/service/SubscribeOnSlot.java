package kz.stech.teachback.subscription.internal.service;

import java.util.UUID;

public interface SubscribeOnSlot {

    void execute(UUID enableSlotId, UUID studentId, String activationCode);
}
