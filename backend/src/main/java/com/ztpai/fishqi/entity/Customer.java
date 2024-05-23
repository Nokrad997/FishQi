package com.ztpai.fishqi.entity;

import lombok.Data;

import com.ztpai.fishqi.DTO.CustomerDTO;

import jakarta.persistence.*;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean is_admin = false;

    public Customer() {
    }

    public Customer(String email, String username, String password, Boolean is_admin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }

    public CustomerDTO convertToDTO() {
        CustomerDTO dto = new CustomerDTO();
        dto.setUserId(this.getUserId());
        dto.setEmail(this.getEmail());
        dto.setPassword(this.getPassword());
        dto.setUsername(this.getUsername());
        dto.setIs_admin(this.getIs_admin());

        return dto;
    }
}