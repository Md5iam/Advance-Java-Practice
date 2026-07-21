package org.example.test1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class IndexControler {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/contact-us")
    public String contactPage(){
        return "contact";
    }

    @PostMapping("/submit-form")
    public String submitForm(@ModelAttribute Contact contact) {
        log.info("Contact information {}" ,contact );
        return "redirect:/";
    }
}


