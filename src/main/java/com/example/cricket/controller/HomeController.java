package com.example.cricket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/re")
public class HomeController {

    @GetMapping("/re")
    public String home() {
        return "<html><body><h1>Welcome to the Live Cricket Scores Application!</h1></body></html>";
    }

    @GetMapping("/test")
public String test() {
    return "Test endpoint working";
}


}

