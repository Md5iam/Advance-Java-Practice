package org.example.hotelmanagmentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagmentsystem.service.BookingService;
import org.example.hotelmanagmentsystem.service.GuestService;
import org.example.hotelmanagmentsystem.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final RoomService roomService;
    private final GuestService guestService;
    private final BookingService bookingService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalRooms", roomService.countTotalRooms());
        model.addAttribute("availableRooms", roomService.countAvailableRooms());
        model.addAttribute("occupiedRooms", roomService.countOccupiedRooms());
        model.addAttribute("totalGuests", guestService.countTotalGuests());
        model.addAttribute("activeBookings", bookingService.countActiveBookings());
        model.addAttribute("totalRevenue", bookingService.getTotalRevenue());
        model.addAttribute("recentBookings", bookingService.getRecentBookings());
        return "dashboard";
    }
}
