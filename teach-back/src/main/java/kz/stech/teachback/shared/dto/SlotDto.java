package kz.stech.teachback.shared.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SlotDto {

    private UUID id;

    private UUID teacherId;

    private Long totalSlots;

    private Long availableSlots;
}
