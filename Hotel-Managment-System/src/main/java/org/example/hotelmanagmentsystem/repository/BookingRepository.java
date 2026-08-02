package org.example.hotelmanagmentsystem.repository;

import org.example.hotelmanagmentsystem.model.Booking;
import org.example.hotelmanagmentsystem.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByGuestId(Long guestId);
    List<Booking> findByRoomId(Long roomId);

    @Query("SELECT SUM(b.totalAmount) FROM Booking b WHERE b.status != 'CANCELLED'")
    Double calculateTotalRevenue();

    List<Booking> findTop5ByOrderByIdDesc();
}
