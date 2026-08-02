package org.example.hotelmanagmentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagmentsystem.model.Booking;
import org.example.hotelmanagmentsystem.service.BookingService;
import org.example.hotelmanagmentsystem.service.GuestService;
import org.example.hotelmanagmentsystem.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;
    private final GuestService guestService;

    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        Booking booking = new Booking();
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(1));

        model.addAttribute("booking", booking);
        model.addAttribute("availableRooms", roomService.getAvailableRooms());
        model.addAttribute("guests", guestService.getAllGuests());
        return "bookings/form";
    }

    @PostMapping("/save")
    public String saveBooking(@ModelAttribute("booking") Booking booking, RedirectAttributes redirectAttributes) {
        try {
            bookingService.createBooking(booking);
            redirectAttributes.addFlashAttribute("successMessage", "Booking created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating booking: " + e.getMessage());
        }
        return "redirect:/bookings";
    }

    @GetMapping("/check-in/{id}")
    public String checkIn(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.checkIn(id);
            redirectAttributes.addFlashAttribute("successMessage", "Guest checked in successfully! Room status updated to Occupied.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bookings";
    }

    @GetMapping("/check-out/{id}")
    public String checkOut(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.checkOut(id);
            redirectAttributes.addFlashAttribute("successMessage", "Guest checked out successfully! Room is now Available.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bookings";
    }

    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(id);
            redirectAttributes.addFlashAttribute("successMessage", "Booking cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bookings";
    }
}
