package com.ztpai.fishqi.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.repositories.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Customer customer = customerRepository.findByEmail(username);
            String auth = customer.getIs_admin() ? "ADMIN" : "USER";
            
            return User.builder()
                    .username(customer.getEmail())
                    .password(customer.getPassword())
                    .authorities(auth)
                    .build();
        } catch (Exception e) {
            
            throw new UsernameNotFoundException("user not found");
        }
    }
}
