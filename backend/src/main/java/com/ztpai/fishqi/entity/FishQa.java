package com.ztpai.fishqi.entity;

import jakarta.persistence.*;

@Entity
public class FishQa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fishQa_id;

    @OneToOne
    @JoinColumn(name = "set_id", nullable = false)
    private FishQSet set;

    @OneToOne
    @JoinColumn(name = "file_id_front", nullable = false)
    private Files fileFront;

    @OneToOne
    @JoinColumn(name = "file_id_back", nullable = false)
    private Files fileBack;

    // Getters and Setters
}
