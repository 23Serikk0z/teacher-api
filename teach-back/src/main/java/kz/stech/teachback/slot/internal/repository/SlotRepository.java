package kz.stech.teachback.slot.internal.repository;

import kz.stech.teachback.shared.dto.SlotDto;
import kz.stech.teachback.slot.internal.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {
    Optional<SlotDto> findByTeacherId(UUID teacherId);

    boolean existsByTeacherId(UUID teacherId);
}