// package com.ztpai.fishqi.services;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.ztpai.fishqi.entity.Customer;
// import com.ztpai.fishqi.repositories.CustomerRepository;

// @Service
// public class CustomerService {
    
//     private CustomerRepository customerRepository;
//     private BCryptPasswordEncoder bCryptPasswordEncoder;

//     @Autowired
//     public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//         this.customerRepository = customerRepository;
//         this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//     }

//     public void registerCustomer(Customer customer) {
//         customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
//         customerRepository.save(customer);
//     }

    
// }
