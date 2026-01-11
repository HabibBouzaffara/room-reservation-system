package com.roomreservation.repository;

import com.roomreservation.entity.BlockedSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedSlotRepository extends JpaRepository<BlockedSlot, Long> {
}
