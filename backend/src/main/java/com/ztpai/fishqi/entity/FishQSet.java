package com.ztpai.fishqi.entity;

import com.ztpai.fishqi.DTO.FishQSetDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
    private String visibility;

    @OneToOne
    @JoinColumn(name = "file_id_image", nullable = true)
    private Files image;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private Customer owner;

    @Column
    private String description;

    public FishQSet(){}

    public FishQSetDTO convertToDTO(){
        FishQSetDTO fishQSetDTO = new FishQSetDTO();
        fishQSetDTO.setSet_id(this.set_id);
        fishQSetDTO.setTitle(this.title);
        fishQSetDTO.setLanguage(this.language);
        fishQSetDTO.setVisibility(this.visibility);
        fishQSetDTO.setOwner_id(this.owner.getUser_id());
        fishQSetDTO.setDescription(this.description);

        if(this.image != null)
            fishQSetDTO.setFtp_image_path(this.image.getFtp_path());
        return fishQSetDTO;
    }
}
