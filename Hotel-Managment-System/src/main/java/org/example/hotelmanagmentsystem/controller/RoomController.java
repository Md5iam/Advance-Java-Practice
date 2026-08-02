package org.example.hotelmanagmentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagmentsystem.model.Room;
import org.example.hotelmanagmentsystem.model.RoomStatus;
import org.example.hotelmanagmentsystem.model.RoomType;
import org.example.hotelmanagmentsystem.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("roomTypes", RoomType.values());
        model.addAttribute("roomStatuses", RoomStatus.values());
        return "rooms/form";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") Room room, RedirectAttributes redirectAttributes) {
        roomService.saveRoom(room);
        redirectAttributes.addFlashAttribute("successMessage", "Room saved successfully!");
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return roomService.getRoomById(id)
                .map(room -> {
                    model.addAttribute("room", room);
                    model.addAttribute("roomTypes", RoomType.values());
                    model.addAttribute("roomStatuses", RoomStatus.values());
                    return "rooms/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Room not found!");
                    return "redirect:/rooms";
                });
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete room. It might have existing bookings.");
        }
        return "redirect:/rooms";
    }
}
