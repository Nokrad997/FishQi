package com.ztpai.fishqi.entity;

import com.ztpai.fishqi.DTO.FishQSetDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FishQSet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String visibility;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "file_id_image", nullable = true)
    private Files image;

    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = true)
    private Customer owner;

    @Column
    private String description;

    public FishQSet(){}

    public FishQSetDTO convertToDTO(){
        FishQSetDTO fishQSetDTO = new FishQSetDTO();
        fishQSetDTO.setSetId(this.setId);
        fishQSetDTO.setTitle(this.title);
        fishQSetDTO.setLanguage(this.language);
        fishQSetDTO.setVisibility(this.visibility);
        fishQSetDTO.setOwner_id(this.owner.getUserId());
        fishQSetDTO.setDescription(this.description);

        if(this.image != null)
            fishQSetDTO.setFtpImagePath(this.image.getFtpPath());
        return fishQSetDTO;
    }
}
