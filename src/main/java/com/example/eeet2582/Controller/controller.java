package com.example.eeet2582.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

    @GetMapping
    String getIndex(Model model) {
        return "index";
    }

    @GetMapping("/homepage")
    String getHome(Model model) {
        return "homepage";
    }
}
