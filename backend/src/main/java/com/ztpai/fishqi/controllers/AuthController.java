package com.ztpai.fishqi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import com.ztpai.fishqi.DTO.LoginDTO;
import com.ztpai.fishqi.DTO.RefreshDTO;
import com.ztpai.fishqi.exceptions.ExpiredRefreshTokenException;
import com.ztpai.fishqi.jsonViews.Views;
import com.ztpai.fishqi.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginDTO customer) {
        try {
            
            return ResponseEntity.ok(this.authService.loginCustomer(customer));
        } catch (BadCredentialsException e) {
            
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            
            return ResponseEntity.internalServerError().body("an erroc occured");
        }
    }

    @JsonView(Views.Public.class)
    @PostMapping(value = "/refresh", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshDTO refresh) {
        try {

            return ResponseEntity.ok().body(this.authService.refreshAccessToken(refresh));
        } catch (ExpiredRefreshTokenException e) {
            
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
