package com.team4.caps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/error")
    public String ErrorPage()
    {
        return "Error";
    }
/*
    @GetMapping("/main")
    public String indexPage()
    {
        return "index";
    }
*/
}
