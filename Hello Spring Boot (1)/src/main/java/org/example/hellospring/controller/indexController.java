package org.example.hellospring.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.hellospring.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class indexController {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/contact")
    public String contactPage(){
        return "contact";
    }

    @PostMapping("/submit-form")
    public String submitForm(@ModelAttribute Contact contact){
//        System.out.println(contact);
        log.info("Contact information {}", contact);
        return "redirect:/contact";

    }
}
