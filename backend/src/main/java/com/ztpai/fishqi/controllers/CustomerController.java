package com.ztpai.fishqi.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.DTO.UpdateCustomerDTO;
import com.ztpai.fishqi.exceptions.UserAlreadyExistsException;
import com.ztpai.fishqi.exceptions.UserDoesntExistException;
import com.ztpai.fishqi.jsonViews.Views;
import com.ztpai.fishqi.services.CustomerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<?> retrieve(@PathVariable Long userId, Authentication auth) {
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

    @GetMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<?> retrieveByEmail(@PathVariable String email) {
        try {
            CustomerDTO user = customerService.getCustomerByEmail(email);

            return ResponseEntity.ok(user);
        } catch (UserDoesntExistException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{userId}", consumes = "application/json", produces = "application/json")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> update(@PathVariable Long userId, @Valid @RequestBody UpdateCustomerDTO customer,
            Authentication auth) {
        try {
            if (!this.customerService.checkIfAdmin(auth.getName())) {
                if (!auth.getName().equals(this.customerService.getCustomerByID(userId).getEmail())) {
                    return ResponseEntity.badRequest().body("You can't update other users");
                }
            }

            CustomerDTO updatedCustomer = customerService.updateCustomer(customer, userId);

            return ResponseEntity.ok(updatedCustomer);

        } catch (NoSuchElementException e) {

            return ResponseEntity.badRequest().body("No customer with given id");

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> store(@Valid @RequestBody CustomerDTO customer, Authentication auth) {
        try {
            if (!this.customerService.checkIfAdmin(auth.getName())) {
                return ResponseEntity.badRequest().body("You can't create a user");
            }

            return ResponseEntity.ok(this.customerService.saveCustomer(customer));
        } catch (UserAlreadyExistsException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable Long userId, Authentication auth) {
        try {
            if (!this.customerService.checkIfAdmin(auth.getName())) {
                if (!auth.getName().equals(this.customerService.getCustomerByID(userId).getEmail())) {
                    return ResponseEntity.badRequest().body("You can't delete other users");
                }
            }

            customerService.deleteCustomer(userId);

            return ResponseEntity.ok("Customer deleted");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
