package kz.stech.teachback.slot.internal.service;


import java.util.UUID;

public interface ICreateSlots {

    void createSlots(UUID teacherId, Long totalSlots);
}
