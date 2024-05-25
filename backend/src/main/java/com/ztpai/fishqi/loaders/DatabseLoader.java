package com.ztpai.fishqi.loaders;

import com.ztpai.fishqi.repositories.CustomerRepository;
import com.ztpai.fishqi.entity.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabseLoader  implements CommandLineRunner{
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabseLoader(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(this.customerRepository.findByEmail("admin@admin.com") == null)
            this.customerRepository.save(new Customer("admin@admin.com", "admin", this.passwordEncoder.encode("admin"), true));
    }
}
