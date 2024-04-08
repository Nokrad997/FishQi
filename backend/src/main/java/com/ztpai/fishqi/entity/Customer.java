package com.ztpai.fishqi.entity;

import jakarta.persistence.*;

@Entity
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean is_admin;

    protected Customer() {}
    public Customer(String email, String username, String password, Boolean is_admin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }
}