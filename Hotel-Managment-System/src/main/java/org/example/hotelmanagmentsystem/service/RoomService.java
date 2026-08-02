package org.example.hotelmanagmentsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagmentsystem.model.Room;
import org.example.hotelmanagmentsystem.model.RoomStatus;
import org.example.hotelmanagmentsystem.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus(RoomStatus.AVAILABLE);
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room saveRoom(Room room) {
        if (room.getStatus() == null) {
            room.setStatus(RoomStatus.AVAILABLE);
        }
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public long countTotalRooms() {
        return roomRepository.count();
    }

    public long countAvailableRooms() {
        return roomRepository.countByStatus(RoomStatus.AVAILABLE);
    }

    public long countOccupiedRooms() {
        return roomRepository.countByStatus(RoomStatus.OCCUPIED);
    }
}
