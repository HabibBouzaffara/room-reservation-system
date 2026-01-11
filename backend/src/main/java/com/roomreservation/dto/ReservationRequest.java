package com.roomreservation.dto;

import com.roomreservation.entity.Reservation;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Activity type is required")
    private Reservation.ActivityType activityType;

    @NotNull(message = "Hardware type is required")
    private Reservation.HardwareType hardwareType;

    @NotBlank(message = "Software type is required")
    @Size(max = 10, message = "Software type should be max 10 characters")
    private String softwareType;

    @NotBlank(message = "Team name is required")
    @Size(max = 10, message = "Team name should be max 10 characters")
    private String teamName;
}
