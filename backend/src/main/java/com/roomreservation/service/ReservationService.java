package com.roomreservation.service;

import com.roomreservation.dto.ReservationRequest;
import com.roomreservation.entity.Reservation;
import com.roomreservation.entity.Room;
import com.roomreservation.entity.User;
import com.roomreservation.repository.ReservationRepository;
import com.roomreservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public Reservation createReservation(ReservationRequest request, User user) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check time slot availability
        if (!isTimeSlotAvailable(room.getId(), request.getStartTime(), request.getEndTime())) {
            throw new RuntimeException("Time slot not available");
        }

        // Create user reservation
        Reservation reservation = Reservation.builder()
                .room(room)
                .user(user)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .activityType(request.getActivityType())
                .hardwareType(request.getHardwareType())
                .softwareType(request.getSoftwareType())
                .teamName(request.getTeamName())
                .adminSlot(false)
                .build();

        Reservation saved = reservationRepository.save(reservation);

        // Auto-create 15min admin buffer slot
        createAdminBuffer(room, request.getEndTime());

        return saved;
    }

    public boolean isTimeSlotAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
                roomId, startTime, endTime);
        return conflicts.isEmpty();
    }

    private void createAdminBuffer(Room room, LocalDateTime afterTime) {
        LocalDateTime bufferEnd = afterTime.plusMinutes(15);

        Reservation adminBuffer = Reservation.builder()
                .room(room)
                .user(null) // No specific user for admin buffer
                .startTime(afterTime)
                .endTime(bufferEnd)
                .activityType(Reservation.ActivityType.QC) // Default
                .hardwareType(Reservation.HardwareType.D5) // Default
                .softwareType("ADMIN")
                .teamName("ADMIN")
                .adminSlot(true)
                .build();

        reservationRepository.save(adminBuffer);
    }

    public List<Reservation> getReservationsByRoom(Long roomId, LocalDateTime date) {
        LocalDateTime dayStart = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime dayEnd = date.withHour(23).withMinute(59).withSecond(59);
        return reservationRepository.findByRoomIdAndDateRange(roomId, dayStart, dayEnd);
    }

    public void deleteReservation(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!reservation.getUser().getId().equals(user.getId()) && !user.getRole().equals(User.Role.ADMIN)) {
            throw new RuntimeException("Not authorized to delete this reservation");
        }

        reservationRepository.delete(reservation);
    }
}
