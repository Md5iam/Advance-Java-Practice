package com.example.labfinal.controller;

import com.example.labfinal.document.PortalBugReport;
import com.example.labfinal.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/portal-bug-report")
    public String getPage(){
        return "page";
    }

    @PostMapping("/portal-bug-report/save")
    public String save(@Valid @ModelAttribute ("pageForm") PortalBugReport data , BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Page Name is required");
        }
        else{
            model.addAttribute("name" , data.getReporterName());
            return "redirect:/getPage";
        }
    }

    @PostMapping("/portal-bug-report/delete/{id}")
    public String delete(Model model, @PathVariable String id){
        reportService.deleteById(id);
        return "redirect:/getPage";
    }
    
}
