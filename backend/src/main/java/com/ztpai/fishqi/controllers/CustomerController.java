package com.ztpai.fishqi.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.exceptions.UserAlreadyExistsException;
import com.ztpai.fishqi.jsonViews.Views;
import com.ztpai.fishqi.services.CustomerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @GetMapping(value = "/{userId}", produces = "application/json")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> retrieve(@PathVariable Long userId) {
        try {
            CustomerDTO user = customerService.getCustomerByID(userId);
            
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {
            
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    
    @GetMapping(value = "/", produces = "application/json")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> retrieveAll() {
        try {
            List<CustomerDTO> customers = this.customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping(value = "/{userId}", consumes = "application/json", produces = "application/json")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> update(@PathVariable Long userId, @Valid @RequestBody CustomerDTO customer) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(customer, userId);
            return ResponseEntity.ok(updatedCustomer);

        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("No customer with given id");

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();

        }

    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @JsonView(Views.Public.class)
    public ResponseEntity<String> store(@Valid @RequestBody CustomerDTO customer) {
        customerService.saveCustomer(customer);

        return ResponseEntity.ok("user is valid");
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable Long userId) {
        try {
            customerService.deleteCustomer(userId);

            return ResponseEntity.ok("Customer deleted");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> registerUser(@Valid @RequestBody CustomerDTO customer) {
        try {
            return ResponseEntity.ok(this.customerService.registerCustomer(customer));

        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
