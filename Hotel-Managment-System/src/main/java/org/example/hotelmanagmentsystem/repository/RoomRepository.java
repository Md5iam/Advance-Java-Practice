package org.example.hotelmanagmentsystem.repository;

import org.example.hotelmanagmentsystem.model.Room;
import org.example.hotelmanagmentsystem.model.RoomStatus;
import org.example.hotelmanagmentsystem.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatus(RoomStatus status);
    List<Room> findByType(RoomType type);
    Optional<Room> findByRoomNumber(String roomNumber);
    boolean existsByRoomNumber(String roomNumber);
    long countByStatus(RoomStatus status);
}
