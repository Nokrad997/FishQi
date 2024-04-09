package com.ztpai.fishqi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ztpai.fishqi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
}
