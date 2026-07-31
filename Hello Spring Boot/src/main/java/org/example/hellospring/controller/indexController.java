package org.example.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/contact")
    public String submitForm(@RequestParam String name,
                             @RequestParam( name = "emailAddress", required = false) String email,
                             @RequestParam String message
                             ){
        System.out.println("Name is " + name);
        System.out.println("Emial is " + email);
        System.out.println("Message is " + message);
        return "contact";
    }
}
