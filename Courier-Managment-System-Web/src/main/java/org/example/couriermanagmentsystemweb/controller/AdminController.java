package org.example.couriermanagmentsystemweb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.couriermanagmentsystemweb.enums.RoleType;
import org.example.couriermanagmentsystemweb.service.CourierService;
import org.example.couriermanagmentsystemweb.service.TransactionService;
import org.example.couriermanagmentsystemweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CourierService courierService;
    private final TransactionService transactionService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalCouriers", courierService.countTotalCouriers());
        model.addAttribute("deliveredCouriers", courierService.countDeliveredCouriers());
        model.addAttribute("totalCustomers", userService.countUsersByRole(RoleType.ROLE_USER));
        model.addAttribute("totalEmployees", userService.countUsersByRole(RoleType.ROLE_EMPLOYEE));
        model.addAttribute("pendingEmployeesCount", userService.getPendingEmployees().size());
        model.addAttribute("totalRevenue", transactionService.getTotalRevenue());
        model.addAttribute("totalCOD", transactionService.getTotalCOD());
        model.addAttribute("totalVolume", transactionService.getTotalTransactionValue());
        model.addAttribute("recentCouriers", courierService.getAllCouriers());
        return "admin/dashboard";
    }

    @GetMapping("/staff-approval")
    public String staffApproval(Model model) {
        model.addAttribute("pendingEmployees", userService.getPendingEmployees());
        return "admin/staff-approval";
    }

    @PostMapping("/approve-staff")
    public String approveStaff(@RequestParam("employeeId") Long employeeId, RedirectAttributes redirectAttributes) {
        try {
            userService.approveEmployee(employeeId);
            redirectAttributes.addFlashAttribute("successMessage", "Employee account approved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/staff-approval";
    }

    @GetMapping("/users")
    public String usersDirectory(Model model) {
        model.addAttribute("customers", userService.getUsersByRole(RoleType.ROLE_USER));
        model.addAttribute("employees", userService.getUsersByRole(RoleType.ROLE_EMPLOYEE));
        return "admin/users";
    }

    @PostMapping("/toggle-user-status")
    public String toggleUserStatus(@RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.toggleUserStatus(userId);
            redirectAttributes.addFlashAttribute("successMessage", "User status updated.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        model.addAttribute("totalRevenue", transactionService.getTotalRevenue());
        model.addAttribute("totalCOD", transactionService.getTotalCOD());
        model.addAttribute("totalVolume", transactionService.getTotalTransactionValue());
        return "admin/transactions";
    }
}
