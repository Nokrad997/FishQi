package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.repositories.CustomerRepository;

@Service
public class CustomerSharedService {
    private final CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    public CustomerSharedService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Customer> getCustomerById(Long id) {
        return this.customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public boolean emailExists(String email) {
        return !this.customerRepository.findByEmail(email).isEmpty();
    }

    public boolean usernameExists(String username) {
        return !this.customerRepository.findByUsername(username).isEmpty();
    }

    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        this.customerRepository.deleteById(id);
    }

    public String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    public Boolean decodePassword(String password, String email) {
        List<Customer> customers = this.customerRepository.findByEmail(email);

        if(customers.isEmpty()) {
            return false;
        }

        return this.passwordEncoder.matches(password, customers.get(0).getPassword());
    }
}
