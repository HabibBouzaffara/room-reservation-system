package com.roomreservation.controller;

import com.roomreservation.dto.ReservationRequest;
import com.roomreservation.entity.Reservation;
import com.roomreservation.entity.User;
import com.roomreservation.service.ReservationService;
import com.roomreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3005", "http://localhost:8092",
        "http://localhost:62276" })
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationRequest request) {
        try {
            // For now, use a default user (will integrate with authentication)
            User user = userService.getUserByEmail("test@example.com")
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Reservation reservation = reservationService.createReservation(request, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-room")
    public ResponseEntity<List<Reservation>> getReservationsByRoom(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return ResponseEntity.ok(reservationService.getReservationsByRoom(roomId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try {
            User user = userService.getUserByEmail("test@example.com")
                    .orElseThrow(() -> new RuntimeException("User not found"));
            reservationService.deleteReservation(id, user);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
