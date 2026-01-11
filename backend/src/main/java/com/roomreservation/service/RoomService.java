package com.roomreservation.service;

import com.roomreservation.entity.Room;
import com.roomreservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public Room getRoomByName(String name) {
        return roomRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }
}
