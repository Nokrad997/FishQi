package com.ztpai.fishqi.services;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.exceptions.UserAlreadyExistsException;

@Service
public class RegistrationService {
    private final CustomerSharedService customerSharedService;

    public RegistrationService(CustomerSharedService customerSharedService) {
        this.customerSharedService = customerSharedService;
    }

    public CustomerDTO registerCustomer(CustomerDTO requestCustomer) throws UserAlreadyExistsException {
        if (this.customerSharedService.emailExists(requestCustomer.getEmail())
                || this.customerSharedService.usernameExists(requestCustomer.getUsername())) {

            throw new UserAlreadyExistsException("User with that email/username already exists");
        }

        requestCustomer.setPassword(this.customerSharedService.encodePassword(requestCustomer.getPassword()));
        Customer savedCustomer = this.customerSharedService.saveCustomer(requestCustomer.convertToEntity());

        return this.convertToDTO(savedCustomer);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setUser_id(customer.getUser_id());
        dto.setEmail(customer.getEmail());
        dto.setPassword(customer.getPassword());
        dto.setUsername(customer.getUsername());
        dto.setIs_admin(customer.getIs_admin());

        return dto;
    }
}
