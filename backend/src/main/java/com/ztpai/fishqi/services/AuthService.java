package com.ztpai.fishqi.services;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.LoginDTO;

@Service
public class AuthService {
    private final CustomerSharedService customerSharedService;

    public AuthService(CustomerSharedService customerSharedService) {
        this.customerSharedService = customerSharedService;
    }

    public Boolean loginCustomer(LoginDTO customer) {
        return this.customerSharedService.decodePassword(customer.getPassword(), customer.getEmail());
    }
}
