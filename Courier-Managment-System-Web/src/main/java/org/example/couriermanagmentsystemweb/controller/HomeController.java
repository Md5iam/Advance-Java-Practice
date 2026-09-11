package org.example.couriermanagmentsystemweb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.couriermanagmentsystemweb.dto.TrackingRequestDto;
import org.example.couriermanagmentsystemweb.entity.Courier;
import org.example.couriermanagmentsystemweb.service.CourierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourierService courierService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("trackingDto", new TrackingRequestDto());
        return "index";
    }

    @GetMapping("/track")
    public String trackPage(Model model) {
        model.addAttribute("trackingDto", new TrackingRequestDto());
        return "track";
    }

    @PostMapping("/track")
    public String processTracking(@Valid @ModelAttribute("trackingDto") TrackingRequestDto dto,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            return "track";
        }

        Optional<Courier> courierOpt = courierService.findByTrackingNumber(dto.getTrackingNumber());
        if (courierOpt.isPresent()) {
            model.addAttribute("courier", courierOpt.get());
        } else {
            model.addAttribute("error", "No courier parcel found with tracking number: " + dto.getTrackingNumber());
        }
        return "track";
    }
}
