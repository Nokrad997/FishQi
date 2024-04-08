package com.ztpai.fishqi.entity;

import jakarta.persistence.*;

@Entity
public class FishQSet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long set_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private Boolean visibility;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Customer owner;

    @Column
    private String description;

    // Getters and Setters
}
