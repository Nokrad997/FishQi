package com.ztpai.fishqi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztpai.fishqi.DTO.LoginDTO;
import com.ztpai.fishqi.services.AuthService;


@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginDTO customer) {
        try {
           
            return ResponseEntity.ok(this.authService.loginCustomer(customer));
        }catch (Error e) {
           
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
