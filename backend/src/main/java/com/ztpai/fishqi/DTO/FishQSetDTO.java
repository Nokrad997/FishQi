package com.ztpai.fishqi.DTO;

import com.ztpai.fishqi.entity.FishQSet;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FishQSetDTO {
    private Long setId;

    // @NotBlank(message = "Title is mandatory")
    private String title;
    // @NotBlank(message = "Language is mandatory")
    private String language;

    // @NotBlank(message = "Visibility is mandatory")
    private String visibility;

    private Long owner_id;

    // @NotBlank(message = "Description is mandatory")
    private String description;

    private String ftpImagePath;

    public FishQSetDTO() {}

    public FishQSet convertToEntity() {
        FishQSet fishQSet = new FishQSet();
        fishQSet.setSetId(this.setId);
        fishQSet.setTitle(this.title);
        fishQSet.setLanguage(this.language);
        fishQSet.setVisibility(this.visibility);
        fishQSet.setDescription(this.description);
        return fishQSet;
    }
}
