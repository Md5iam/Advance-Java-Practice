package com.example.labfinal.controller;

import com.example.labfinal.dto.CaseRequestDTO;
import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.model.Priority;
import com.example.labfinal.service.CaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cases")
@RequiredArgsConstructor
public class CaseViewController {

    private final CaseService caseService;

    @GetMapping
    public String listCases(
            @RequestParam(name = "search", required = false) String search,
            Model model
    ) {
        model.addAttribute("cases", caseService.searchCases(search));
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("statuses", CaseStatus.values());
        model.addAttribute("searchQuery", search != null ? search : "");
        if (!model.containsAttribute("caseForm")) {
            model.addAttribute("caseForm", new CaseRequestDTO());
        }
        return "cases";
    }

    @PostMapping("/add")
    public String addCase(
            @Valid @ModelAttribute("caseForm") CaseRequestDTO dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cases", caseService.getAllCases());
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("statuses", CaseStatus.values());
            return "cases";
        }
        try {
            caseService.createCase(dto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("cases", caseService.getAllCases());
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("statuses", CaseStatus.values());
            return "cases";
        }
        return "redirect:/cases?success=true";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(
            @PathVariable String id,
            @RequestParam("status") CaseStatus status
    ) {
        try {
            caseService.updateCaseStatus(id, status);
            return "redirect:/cases?updated=true";
        } catch (Exception e) {
            return "redirect:/cases?error=" + e.getMessage();
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCase(@PathVariable String id) {
        caseService.deleteCase(id);
        return "redirect:/cases?deleted=true";
    }
}
