package org.example.hotelmanagmentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagmentsystem.model.Guest;
import org.example.hotelmanagmentsystem.service.GuestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping
    public String listGuests(Model model) {
        model.addAttribute("guests", guestService.getAllGuests());
        return "guests/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("guest", new Guest());
        return "guests/form";
    }

    @PostMapping("/save")
    public String saveGuest(@ModelAttribute("guest") Guest guest, RedirectAttributes redirectAttributes) {
        guestService.saveGuest(guest);
        redirectAttributes.addFlashAttribute("successMessage", "Guest saved successfully!");
        return "redirect:/guests";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return guestService.getGuestById(id)
                .map(guest -> {
                    model.addAttribute("guest", guest);
                    return "guests/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Guest not found!");
                    return "redirect:/guests";
                });
    }

    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            guestService.deleteGuest(id);
            redirectAttributes.addFlashAttribute("successMessage", "Guest deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete guest. Guest might have active bookings.");
        }
        return "redirect:/guests";
    }
}
