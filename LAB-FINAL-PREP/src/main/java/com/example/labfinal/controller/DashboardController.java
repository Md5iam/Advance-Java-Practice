package com.example.labfinal.controller;

import com.example.labfinal.dto.DashboardStatsDTO;
import com.example.labfinal.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        model.addAttribute("stats", stats);
        return "dashboard";
    }
}
