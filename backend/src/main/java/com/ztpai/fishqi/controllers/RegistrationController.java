package com.ztpai.fishqi.controllers;

import com.ztpai.fishqi.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepository customerRepository;


}
