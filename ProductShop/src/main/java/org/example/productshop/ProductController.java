package org.example.productshop;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("name", "Mr Java");
        model.addAttribute("product", new Product());
        return "form";
    }

    @PostMapping("/add")
    public String submit(@Valid @ModelAttribute Product product, BindingResult bindingResult){
        log.info("product {} has been submited ", product);
        return "redirect:/product/add";
    }
}
