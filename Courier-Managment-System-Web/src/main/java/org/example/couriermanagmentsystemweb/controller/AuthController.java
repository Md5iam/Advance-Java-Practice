package org.example.couriermanagmentsystemweb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.couriermanagmentsystemweb.dto.UserRegisterDto;
import org.example.couriermanagmentsystemweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setRoleType("USER");
        model.addAttribute("userDto", dto);
        return "auth/register";
    }

    @PostMapping("/register")
    public String processCustomerRegister(@Valid @ModelAttribute("userDto") UserRegisterDto dto,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            dto.setRoleType("USER");
            userService.registerUser(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please sign in.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/employee/register")
    public String employeeRegisterPage(Model model) {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setRoleType("EMPLOYEE");
        model.addAttribute("userDto", dto);
        return "auth/employee-register";
    }

    @PostMapping("/employee/register")
    public String processEmployeeRegister(@Valid @ModelAttribute("userDto") UserRegisterDto dto,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/employee-register";
        }

        try {
            dto.setRoleType("EMPLOYEE");
            userService.registerUser(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Employee registration submitted! Pending Admin approval.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/employee/register";
        }
    }
}
