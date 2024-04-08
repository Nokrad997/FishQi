package com.ztpai.fishqi.entity;

import jakarta.persistence.*;

@Entity
public class Files {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;

    @Column(nullable = false)
    private String ftp_path;

    // Getters and Setters
}
