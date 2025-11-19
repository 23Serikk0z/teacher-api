package kz.stech.teachback.slot.internal.repository;

import kz.stech.teachback.slot.internal.model.EnableSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnableSlotRepository extends JpaRepository<EnableSlot, UUID> {
}