package kz.stech.teachback.slot.internal.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "slots")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID teacherId;

    private Long totalSlots;

    private Long availableSlots;
}
