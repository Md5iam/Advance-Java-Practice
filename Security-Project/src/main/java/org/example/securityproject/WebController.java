package org.example.securityproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(){
        return "dashboard";
    }

    @GetMapping("sign-up")
    public String signup(){
        return "sign-up";
    }

    @GetMapping("privacy")
    public String privacy(){
        return "privacy";
    }

    @GetMapping("sign-in")
    public String signin(){
        return "sign-in";
    }


}
