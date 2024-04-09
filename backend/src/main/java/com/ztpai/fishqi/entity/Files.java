package com.ztpai.fishqi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Files {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;

    @Column(nullable = false)
    private String ftp_path;
}
