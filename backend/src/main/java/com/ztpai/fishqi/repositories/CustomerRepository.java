package com.ztpai.fishqi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ztpai.fishqi.entity.Customer;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long>{
    List<Customer> findByEmail(String email);
}
