package com.microcookie.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for serving the frontend dashboard
 */
@Controller
public class DashboardController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "forward:/index.html";
    }
}