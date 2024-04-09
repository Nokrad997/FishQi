package com.ztpai.fishqi.controllers;

import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.services.CustomerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/{userId}",produces = "application/json")
    public ResponseEntity<?> retrieve(@PathVariable Long userId) {
        CustomerDTO user = customerService.getCustomerByID(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<CustomerDTO>> retrieveAll() {
        List<CustomerDTO> customers = this.customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping(value = "/{userId}", consumes = "application/json", produces = "application/json")
    public String update(@PathVariable Long userId, @RequestBody Customer customer) {
        customerService.updateCustomer(customer, userId);
        return "Customer updated";
    }
    
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return "Customer created";
    }

    @DeleteMapping(value="/{userId}", produces = "application/json")
    public String delete(@PathVariable Long userId) {
        customerService.deleteCustomer(userId);
        return "Customer deleted";
    }
}
