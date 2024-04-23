package com.ztpai.fishqi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztpai.fishqi.DTO.JwtDTO;
import com.ztpai.fishqi.DTO.LoginDTO;
import com.ztpai.fishqi.config.JwtTokenUtil;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.services.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/login/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginDTO customer) {
        try {
            Customer signedInCustomer = this.authService.loginCustomer(customer);
            if (signedInCustomer.getUser_id() != null) {
                String access = this.jwtTokenUtil.generateToken(signedInCustomer.getEmail(), signedInCustomer.getIs_admin());
                String refresh = this.jwtTokenUtil.generateRefreshToken(signedInCustomer.getEmail());

                return ResponseEntity.ok(new JwtDTO(access, refresh));    
            }
            
            return ResponseEntity.badRequest().body("Wrong email or password");
        }catch (Error e) {
           
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
