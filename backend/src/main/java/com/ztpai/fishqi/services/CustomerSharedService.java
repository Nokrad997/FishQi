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

    public Customer getCustomerByEmail(String email) {
        try {
            return this.customerRepository.findByEmail(email);
        } catch (Exception e) {
            return new Customer();
        }
    }

    public boolean emailExists(String email) {
        return this.customerRepository.findByEmail(email).getEmail() != null;
    }

    public boolean usernameExists(String username) {
        return this.customerRepository.findByUsername(username).getEmail() != null;
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
        Customer customer = this.customerRepository.findByEmail(email);

        if (customer.getEmail() == null) {
            return false;
        }

        return this.passwordEncoder.matches(password, customer.getPassword());
    }
}
