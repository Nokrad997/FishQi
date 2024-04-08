package com.ztpai.fishqi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ztpai.fishqi.repositories.CustomerRepository;

@Controller
public class AppController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }
}
