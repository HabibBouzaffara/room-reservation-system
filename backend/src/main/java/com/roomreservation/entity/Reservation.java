package com.roomreservation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations",
       uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "start_time", "end_time"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    public enum ActivityType { COVERAGE, FULL_VALIDATION, QC }
    public enum HardwareType { D5, D4 }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ActivityType activityType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HardwareType hardwareType;

    @Column(nullable = false, length = 10)
    private String softwareType;

    @Column(nullable = false, length = 10)
    private String teamName;

    @Column(nullable = false)
    private boolean adminSlot; // 15min buffer

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
