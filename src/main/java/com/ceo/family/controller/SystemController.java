package com.ceo.family.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping(value = "")
public class SystemController {
    @GetMapping("/login")
    public String sys() {
        return "sys";
    }
    @GetMapping("/header")
    public String header() {
        return "header";
    }
    @GetMapping("/footer")
    public String footer() {
        return "footer";
    }
}
