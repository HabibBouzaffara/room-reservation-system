package com.roomreservation.repository;

import com.roomreservation.entity.BlockedSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BlockedSlotRepository extends JpaRepository<BlockedSlot, Long> {

    @Query("SELECT bs FROM BlockedSlot bs WHERE bs.room.id = :roomId " +
            "AND bs.startTime >= :dayStart AND bs.endTime <= :dayEnd " +
            "ORDER BY bs.startTime ASC")
    List<BlockedSlot> findByRoomIdAndDateRange(
            @Param("roomId") Long roomId,
            @Param("dayStart") LocalDateTime dayStart,
            @Param("dayEnd") LocalDateTime dayEnd);
}
