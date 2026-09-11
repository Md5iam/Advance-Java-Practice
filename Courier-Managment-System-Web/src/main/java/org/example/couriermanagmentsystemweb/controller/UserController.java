package org.example.couriermanagmentsystemweb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.couriermanagmentsystemweb.dto.CourierBookingDto;
import org.example.couriermanagmentsystemweb.entity.Courier;
import org.example.couriermanagmentsystemweb.entity.Transaction;
import org.example.couriermanagmentsystemweb.entity.User;
import org.example.couriermanagmentsystemweb.service.CourierService;
import org.example.couriermanagmentsystemweb.service.TransactionService;
import org.example.couriermanagmentsystemweb.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CourierService courierService;
    private final TransactionService transactionService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<Courier> userCouriers = courierService.getCouriersBySender(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("totalBookings", userCouriers.size());
        model.addAttribute("recentCouriers", userCouriers);
        return "user/dashboard";
    }

    @GetMapping("/book")
    public String showBookingForm(Model model) {
        model.addAttribute("bookingDto", new CourierBookingDto());
        return "user/book";
    }

    @PostMapping("/book")
    public String processBooking(@AuthenticationPrincipal UserDetails userDetails,
                                 @Valid @ModelAttribute("bookingDto") CourierBookingDto dto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/book";
        }

        try {
            User sender = userService.findByEmail(userDetails.getUsername()).orElseThrow();
            Courier courier = courierService.bookCourier(dto, sender);
            redirectAttributes.addFlashAttribute("successMessage", "Courier booked successfully! Tracking ID: " + courier.getTrackingNumber());
            return "redirect:/user/history";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error booking courier: " + e.getMessage());
            return "redirect:/user/book";
        }
    }

    @GetMapping("/history")
    public String history(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("couriers", courierService.getCouriersBySender(user.getId()));
        return "user/history";
    }

    @GetMapping("/invoice/{id}")
    public String invoice(@PathVariable Long id, Model model) {
        Optional<Courier> courierOpt = courierService.findById(id);
        if (courierOpt.isPresent()) {
            Courier courier = courierOpt.get();
            Optional<Transaction> transactionOpt = transactionService.getTransactionByCourierId(courier.getId());
            model.addAttribute("courier", courier);
            model.addAttribute("transaction", transactionOpt.orElse(null));
            return "user/invoice";
        }
        return "redirect:/user/history";
    }

    @GetMapping("/cancel/{id}")
    public String cancelCourier(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
            courierService.cancelCourier(id, user);
            redirectAttributes.addFlashAttribute("successMessage", "Courier booking cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/history";
    }
}
