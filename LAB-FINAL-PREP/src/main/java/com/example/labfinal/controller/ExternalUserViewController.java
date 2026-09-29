package com.example.labfinal.controller;

import com.example.labfinal.dto.ExternalUserDTO;
import com.example.labfinal.service.ExternalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/external-users")
@RequiredArgsConstructor
public class ExternalUserViewController {

    private final ExternalUserService externalUserService;

    @GetMapping
    public String showExternalUsers(Model model) {
        long startTime = System.currentTimeMillis();
        List<ExternalUserDTO> users = externalUserService.fetchExternalUsers();
        long duration = System.currentTimeMillis() - startTime;

        model.addAttribute("users", users);
        model.addAttribute("fetchDurationMs", duration);
        return "external-users";
    }

    @PostMapping("/evict")
    public String evictCache() {
        externalUserService.evictCache();
        return "redirect:/external-users?cacheEvicted=true";
    }
}
