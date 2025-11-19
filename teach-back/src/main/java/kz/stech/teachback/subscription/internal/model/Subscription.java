package kz.stech.teachback.subscription.internal.model;


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
@Table(name = "subscriptions")
public class Subscription {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID teacherId;

    @Column(nullable = false)
    private UUID studentId;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID enableSlotId;

    @CreationTimestamp
    private LocalDateTime subscriptionDate;
}
