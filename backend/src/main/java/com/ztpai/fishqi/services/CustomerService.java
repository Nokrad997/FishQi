package com.ztpai.fishqi.services;

import java.util.Optional;

import java.util.List;
import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.exceptions.UserAlreadyExistsException;

@Service
public class CustomerService {

    private final CustomerSharedService customerSharedService;

    public CustomerService(CustomerSharedService customerSharedService) {
        this.customerSharedService = customerSharedService;
    }

    public CustomerDTO getCustomerByID(Long userId) {
        Optional<Customer> optCus = this.customerSharedService.getCustomerById(userId);
        Customer customer = optCus.orElseThrow();

        return customer.convertToDTO();
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = this.customerSharedService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream().map(Customer::convertToDTO).toList();

        return customerDTOs;
    }

    public CustomerDTO updateCustomer(CustomerDTO requestCustomer, Long userId) {
        Optional<Customer> OptCus = this.customerSharedService.getCustomerById(userId);
        Customer customer = OptCus.orElseThrow();

        customer.setEmail(requestCustomer.getEmail());
        customer.setUsername(requestCustomer.getUsername());
        customer.setIs_admin(requestCustomer.getIs_admin());
        customer.setPassword(requestCustomer.getPassword());

        this.customerSharedService.saveCustomer(customer);

        return customer.convertToDTO();
    }

    public CustomerDTO saveCustomer(CustomerDTO requestCustomer) throws UserAlreadyExistsException {
        if (this.customerSharedService.emailExists(requestCustomer.getEmail())
                || this.customerSharedService.usernameExists(requestCustomer.getUsername())) {
            throw new UserAlreadyExistsException("User with that email/username already exists");
        }
        requestCustomer.setPassword(this.customerSharedService.encodePassword(requestCustomer.getPassword()));
        Customer savedCustomer = customerSharedService.saveCustomer(requestCustomer.convertToEntity());

        return savedCustomer.convertToDTO();
    }

    public void deleteCustomer(Long userId) {
        this.customerSharedService.deleteCustomer(userId);
    }

    public boolean checkIfAdmin(String email) {
        Customer customer = this.customerSharedService.getCustomerByEmail(email);
        
        return customer.getIs_admin();
    }
}
