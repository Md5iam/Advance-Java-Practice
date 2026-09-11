package org.example.couriermanagmentsystemweb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.couriermanagmentsystemweb.entity.Courier;
import org.example.couriermanagmentsystemweb.entity.User;
import org.example.couriermanagmentsystemweb.enums.CourierStatus;
import org.example.couriermanagmentsystemweb.service.CourierService;
import org.example.couriermanagmentsystemweb.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final UserService userService;
    private final CourierService courierService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        String zone = employee.getAssignedZone() != null ? employee.getAssignedZone() : "";

        // Pickup tasks: parcels this employee picked up (PICKED_UP status)
        List<Courier> pickupTasks = courierService.getCouriersByPickupEmployee(employee.getId());

        // Pending pickups in this employee's zone
        List<Courier> pendingZonePickups = courierService.getCouriersByPickupZoneAndStatus(zone, CourierStatus.PENDING);

        // Incoming parcels: IN_TRANSIT parcels heading to this employee's zone for delivery
        List<Courier> incomingParcels = courierService.getCouriersByDeliveryZoneAndStatus(zone, CourierStatus.IN_TRANSIT);

        // Delivery tasks: parcels this employee claimed for delivery (OUT_FOR_DELIVERY)
        List<Courier> deliveryTasks = courierService.getCouriersByDeliveryEmployee(employee.getId());

        model.addAttribute("employee", employee);
        model.addAttribute("pendingZoneCount", pendingZonePickups.size());
        model.addAttribute("pickupTaskCount", pickupTasks.stream()
                .filter(c -> c.getStatus() == CourierStatus.PICKED_UP).count());
        model.addAttribute("incomingCount", incomingParcels.size());
        model.addAttribute("deliveryTaskCount", deliveryTasks.stream()
                .filter(c -> c.getStatus() == CourierStatus.OUT_FOR_DELIVERY).count());
        model.addAttribute("recentDeliveries", pickupTasks);
        return "employee/dashboard";
    }

    // ============ PICKUP ZONE OPERATIONS ============

    @GetMapping("/pickups")
    public String pickups(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        String zone = employee.getAssignedZone() != null ? employee.getAssignedZone() : "";
        List<Courier> pendingPickups = courierService.getCouriersByPickupZoneAndStatus(zone, CourierStatus.PENDING);
        model.addAttribute("pickups", pendingPickups);
        model.addAttribute("employee", employee);
        return "employee/pickups";
    }

    @PostMapping("/claim-pickup")
    public String claimPickup(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam("courierId") Long courierId,
                              RedirectAttributes redirectAttributes) {
        try {
            User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();
            courierService.updateCourierStatus(courierId, CourierStatus.PICKED_UP, employee);
            redirectAttributes.addFlashAttribute("successMessage", "Parcel claimed for pickup successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/employee/pickups";
    }

    @PostMapping("/send-transit")
    public String sendToTransit(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam("courierId") Long courierId,
                                RedirectAttributes redirectAttributes) {
        try {
            User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();
            courierService.updateCourierStatus(courierId, CourierStatus.IN_TRANSIT, employee);
            redirectAttributes.addFlashAttribute("successMessage", "Parcel sent to delivery hub (IN TRANSIT)!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/employee/deliveries";
    }

    // ============ DELIVERY ZONE OPERATIONS ============

    @GetMapping("/incoming")
    public String incoming(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        String zone = employee.getAssignedZone() != null ? employee.getAssignedZone() : "";
        List<Courier> incomingParcels = courierService.getCouriersByDeliveryZoneAndStatus(zone, CourierStatus.IN_TRANSIT);
        model.addAttribute("incoming", incomingParcels);
        model.addAttribute("employee", employee);
        return "employee/incoming";
    }

    @PostMapping("/claim-delivery")
    public String claimDelivery(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam("courierId") Long courierId,
                                RedirectAttributes redirectAttributes) {
        try {
            User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();
            courierService.updateCourierStatus(courierId, CourierStatus.OUT_FOR_DELIVERY, employee);
            redirectAttributes.addFlashAttribute("successMessage", "Parcel claimed for delivery! It is now OUT FOR DELIVERY.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/employee/incoming";
    }

    @PostMapping("/mark-delivered")
    public String markDelivered(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam("courierId") Long courierId,
                                RedirectAttributes redirectAttributes) {
        try {
            User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();
            courierService.updateCourierStatus(courierId, CourierStatus.DELIVERED, employee);
            redirectAttributes.addFlashAttribute("successMessage", "Parcel delivered successfully! Payment marked as PAID.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/employee/deliveries";
    }

    // ============ COMBINED VIEW ============

    @GetMapping("/deliveries")
    public String deliveries(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User employee = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        // Pickup tasks: parcels this employee picked up (needs to send to transit)
        List<Courier> pickupTasks = courierService.getCouriersByPickupEmployee(employee.getId());

        // Delivery tasks: parcels this employee claimed for delivery
        List<Courier> deliveryTasks = courierService.getCouriersByDeliveryEmployee(employee.getId());

        model.addAttribute("pickupTasks", pickupTasks);
        model.addAttribute("deliveryTasks", deliveryTasks);
        model.addAttribute("employee", employee);
        return "employee/deliveries";
    }
}
