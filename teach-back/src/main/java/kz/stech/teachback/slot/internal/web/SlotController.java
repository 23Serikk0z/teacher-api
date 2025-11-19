package kz.stech.teachback.slot.internal.web;


import kz.stech.teachback.slot.internal.service.OpenAccessToSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/slot")
@RequiredArgsConstructor
public class SlotController {

    private final OpenAccessToSlot openAccessToSlot;

    @PostMapping("/enable/slot")
    ResponseEntity<Object> openAccessToSlot(@RequestParam UUID teacherId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(openAccessToSlot.execute(teacherId));
    }
}
