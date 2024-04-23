package com.ztpai.fishqi.services;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.LoginDTO;
import com.ztpai.fishqi.entity.Customer;

@Service
public class AuthService {
    private final CustomerSharedService customerSharedService;

    public AuthService(CustomerSharedService customerSharedService) {
        this.customerSharedService = customerSharedService;
    }

    public Customer loginCustomer(LoginDTO customer) {
        if(this.customerSharedService.decodePassword(customer.getPassword(), customer.getEmail())) {
            Customer signedCustomer = this.customerSharedService.getCustomerByEmail(customer.getEmail());

            return signedCustomer;
        }

        return new Customer();
    }
}
