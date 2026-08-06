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

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/add")
    public String showForm(Model model){
//        model.addAttribute("name", "Siam Ahmed");
        model.addAttribute("product" , new Product());
        return "form";
    }

    @PostMapping("/add")
    public String submit(@Valid @ModelAttribute Product product, BindingResult bindingResult){// form er data "product" object e map kore
        // bindingResult = error in frontend mapping
        if( bindingResult.hasErrors()){
            return "form";
        }
        log.info("Product {} has been submitted", product);
        return "redirect:/product/add";
    }
}
