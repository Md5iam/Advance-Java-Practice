package org.example.courierdemo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CourierController {

    private final CourierRepository courierRepository;

    public CourierController(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    // Scene 1: Book Courier Page
    @GetMapping({"/", "/book"})
    public String showBookScene(Model model) {
        model.addAttribute("courier", new Courier());
        return "book";
    }

    // Process Booking Form Submission with @Valid validation
    @PostMapping("/book")
    public String processBooking(@Valid @ModelAttribute("courier") Courier courier, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "book";
        }

        // Generate unique tracking number
        String trackingNo = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        courier.setTrackingNumber(trackingNo);
        courier.setStatus("PENDING");

        courierRepository.save(courier);

        model.addAttribute("message", "Courier booked successfully!");
        model.addAttribute("trackingNumber", trackingNo);
        model.addAttribute("courier", new Courier()); // reset form
        return "book";
    }

    // Scene 2: Track Package Page
    @GetMapping("/track")
    public String showTrackScene(Model model) {
        model.addAttribute("trackRequest", new TrackRequest());
        return "track";
    }

    // Process Track Search with @Valid validation
    @PostMapping("/track")
    public String processTracking(@Valid @ModelAttribute("trackRequest") TrackRequest trackRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "track";
        }

        Optional<Courier> courierOpt = courierRepository.findByTrackingNumber(trackRequest.getTrackingNumber().trim());
        if (courierOpt.isPresent()) {
            model.addAttribute("courier", courierOpt.get());
        } else {
            model.addAttribute("error", "No courier found with tracking number: " + trackRequest.getTrackingNumber());
        }
        return "track";
    }

    // Scene 3: Courier History / Management Page
    @GetMapping("/history")
    public String showHistoryScene(Model model) {
        List<Courier> couriers = courierRepository.findAll();
        model.addAttribute("couriers", couriers);
        return "history";
    }

    // Update Status Action using ModelAttribute (no @RequestParam)
    @PostMapping("/update-status")
    public String updateStatus(@ModelAttribute Courier courier) {
        if (courier.getId() != null) {
            Optional<Courier> courierOpt = courierRepository.findById(courier.getId());
            if (courierOpt.isPresent()) {
                Courier existing = courierOpt.get();
                existing.setStatus(courier.getStatus());
                courierRepository.save(existing);
            }
        }
        return "redirect:/history";
    }

    // Delete Action using PathVariable (no @RequestParam)
    @PostMapping("/delete/{id}")
    public String deleteCourier(@PathVariable("id") Long id) {
        courierRepository.deleteById(id);
        return "redirect:/history";
    }
}
