package com.ztpai.fishqi.loaders;

import com.ztpai.fishqi.repositories.CustomerRepository;
import com.ztpai.fishqi.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabseLoader  implements CommandLineRunner{
    private final CustomerRepository customerRepository;

    @Autowired
    public DatabseLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.customerRepository.save(new Customer("admin@admin.com", "admin", "pwd", true));
    }
}