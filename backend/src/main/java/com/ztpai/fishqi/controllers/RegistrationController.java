package com.ztpai.fishqi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.ztpai.fishqi.DTO.RegistrationDTO;
import com.ztpai.fishqi.exceptions.UserAlreadyExistsException;
import com.ztpai.fishqi.jsonViews.Views;
import com.ztpai.fishqi.services.RegistrationService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class RegistrationController {
    private final RegistrationService registrationService;
    
    public RegistrationController (RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegistrationDTO customer) {
        try {
            
            return ResponseEntity.ok(this.registrationService.registerCustomer(customer));
        } catch (UserAlreadyExistsException e) {
           
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
