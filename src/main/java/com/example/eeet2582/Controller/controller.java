package com.example.eeet2582.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

    @GetMapping
    String getHome(Model model) {
        return "homepage";
    }
}
