package org.example.hotelmanagmentsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagmentsystem.model.Booking;
import org.example.hotelmanagmentsystem.model.BookingStatus;
import org.example.hotelmanagmentsystem.model.Room;
import org.example.hotelmanagmentsystem.model.RoomStatus;
import org.example.hotelmanagmentsystem.repository.BookingRepository;
import org.example.hotelmanagmentsystem.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        long days = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        if (days <= 0) {
            days = 1;
        }

        double totalAmount = room.getPricePerNight() * days;
        booking.setTotalAmount(totalAmount);
        booking.setRoom(room);
        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    @Transactional
    public void checkIn(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        booking.setStatus(BookingStatus.CHECKED_IN);
        Room room = booking.getRoom();
        room.setStatus(RoomStatus.OCCUPIED);

        roomRepository.save(room);
        bookingRepository.save(booking);
    }

    @Transactional
    public void checkOut(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        booking.setStatus(BookingStatus.CHECKED_OUT);
        Room room = booking.getRoom();
        room.setStatus(RoomStatus.AVAILABLE);

        roomRepository.save(room);
        bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        Room room = booking.getRoom();
        if (room.getStatus() == RoomStatus.OCCUPIED) {
            room.setStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
        }

        bookingRepository.save(booking);
    }

    public long countActiveBookings() {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.CHECKED_IN)
                .count();
    }

    public Double getTotalRevenue() {
        Double total = bookingRepository.calculateTotalRevenue();
        return total != null ? total : 0.0;
    }

    public List<Booking> getRecentBookings() {
        return bookingRepository.findTop5ByOrderByIdDesc();
    }
}
