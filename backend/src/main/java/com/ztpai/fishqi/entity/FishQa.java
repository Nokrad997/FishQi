package com.ztpai.fishqi.entity;

import com.ztpai.fishqi.DTO.FishQDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FishQa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fishQaId;

    @OneToOne
    @JoinColumn(name = "set_id", nullable = false)
    private FishQSet set;
    
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "file_id", nullable = true)
    private Files file;

    public FishQDTO convertToDTO() {
        FishQDTO fishQDTO = new FishQDTO();

        fishQDTO.setSetId(this.getSet().getSetId());
        fishQDTO.setFtpWordsPath(this.getFile().getFtpPath());

        return fishQDTO;
    }
}
