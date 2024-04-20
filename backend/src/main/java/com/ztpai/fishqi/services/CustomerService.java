package com.ztpai.fishqi.services;

import java.util.Optional;

import java.util.List;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.exceptions.UserAlreadyExistsException;
import com.ztpai.fishqi.repositories.CustomerRepository;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO getCustomerByID(Long userId) {
        Optional<Customer> optCus = this.customerRepository.findById(userId);
        Customer customer = optCus.orElseThrow();

        return convertToDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream().map(this::convertToDTO).toList();

        return customerDTOs;
    }

    public CustomerDTO updateCustomer(CustomerDTO requestCustomer, Long userId) {
        Optional<Customer> OptCus = this.customerRepository.findById(userId);
        Customer customer = OptCus.orElseThrow();

        customer.setEmail(requestCustomer.getEmail());
        customer.setUsername(requestCustomer.getUsername());
        customer.setIs_admin(requestCustomer.getIs_admin());
        customer.setPassword(requestCustomer.getPassword());

        this.customerRepository.save(customer);

        return this.convertToDTO(customer);
    }

    public CustomerDTO saveCustomer(CustomerDTO requestCustomer) {
        // customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = customerRepository.save(this.convertToEntity(requestCustomer));

        return this.convertToDTO(savedCustomer);
    }

    public void deleteCustomer(Long userId) {
        this.customerRepository.deleteById(userId);
    }

    public CustomerDTO registerCustomer(CustomerDTO customer) throws UserAlreadyExistsException {
        if (this.emailExists(customer.getEmail()) || this.usernameExists(customer.getUsername())) {
            
            throw new UserAlreadyExistsException("User with that email/username already exists");
        }
        Customer savedCustomer = this.customerRepository.save(this.convertToEntity(customer));

        return this.convertToDTO(savedCustomer);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setUser_id(customer.getUser_id());
        dto.setEmail(customer.getEmail());
        dto.setUsername(customer.getUsername());
        dto.setIs_admin(customer.getIs_admin());

        return dto;
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setEmail(customerDTO.getEmail());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setIs_admin(customerDTO.getIs_admin());

        return customer;
    }

    private boolean emailExists(String email) {
        return !this.customerRepository.findByEmail(email).isEmpty();
    }

    private boolean usernameExists(String username) {
        return !this.customerRepository.findByUsername(username).isEmpty();
    }
}
