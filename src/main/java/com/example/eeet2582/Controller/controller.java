package com.example.eeet2582.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {


     @GetMapping("/account")
    String getAccount(Model model) {
        return "account";
    }

    @GetMapping("/edit")
    String getEdit(Model model) {
        return "edit";
    }

    @GetMapping("/homepage")
    String getHome(Model model) {
        return "homepage";
    }

    @GetMapping
    String getIndex(Model model) {
        return "index";
    }

    

}
