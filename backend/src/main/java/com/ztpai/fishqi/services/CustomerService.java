package com.ztpai.fishqi.services;

import java.util.Optional;

import java.util.List;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    private CustomerRepository customerRepository;
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO getCustomerByID(Long userId){
        Optional<Customer> optCus = this.customerRepository.findById(userId);
        Customer customer = optCus.get();

        if(customer == null) {
            throw new IllegalArgumentException("No user with id " + userId);
        }

        return convertToDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream().map(this::convertToDTO).toList();

        return customerDTOs;
    }

    public String updateCustomer(Customer requestCustomer, Long userId) {
        Optional<Customer> OptCus = this.customerRepository.findById(userId);   
        Customer customer = OptCus.get(); 

        if(customer == null) {
            throw new IllegalArgumentException("No user with id " + userId);
        }

        customer.setEmail(requestCustomer.getEmail());
        customer.setUsername(requestCustomer.getUsername());
        customer.setIs_admin(requestCustomer.getIs_admin());
        customer.setPassword(requestCustomer.getPassword());

        this.customerRepository.save(customer);

        return "działa";
    }

    public String saveCustomer(Customer requestCustomer) {
        // customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customerRepository.save(requestCustomer);

        return "działa";
    }

    public void deleteCustomer(Long userId){
        this.customerRepository.deleteById(userId);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setUser_id(customer.getUser_id());
        dto.setEmail(customer.getEmail());
        dto.setUsername(customer.getUsername());
        dto.setIs_admin(customer.getIs_admin());

        return dto;
    }

    
}
