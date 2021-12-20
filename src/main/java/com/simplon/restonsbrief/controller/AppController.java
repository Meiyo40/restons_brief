package com.simplon.restonsbrief.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {

    @GetMapping(value = {"/index", "/"})
    public String getIndex(Model model, HttpServletRequest request) {
        return "index";
    }
}
