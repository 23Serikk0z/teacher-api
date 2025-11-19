package kz.stech.teachback.slot.internal.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "enabled_slots")
public class EnableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "slot_id", nullable = false, updatable = false)
    private Slot slot;
    
    private String activationCode;

    private Boolean isActive;

    @CreationTimestamp
    private LocalDateTime slotEnabledDateTime;

    private LocalDateTime slotDisabledDateTime;
}
