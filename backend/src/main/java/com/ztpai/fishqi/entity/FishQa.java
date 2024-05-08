package com.ztpai.fishqi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FishQa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fishQa_id;

    @OneToOne
    @JoinColumn(name = "set_id", nullable = false)
    private FishQSet set;
    
    @OneToOne
    @JoinColumn(name = "file_id", nullable = true)
    private Files file;
}
