package kz.stech.teachback.subscription.internal.web;

import kz.stech.teachback.subscription.internal.service.SubscribeOnSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/subscribe")
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeOnSlot subscribeOnSlot;

    @PostMapping
    public void subscribe(@RequestParam UUID enableSlotId, @RequestParam UUID studentId, @RequestParam String activationCode) {
        subscribeOnSlot.execute(enableSlotId, studentId, activationCode);
    }
}
