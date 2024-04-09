package com.ztpai.fishqi.DTO;

import com.ztpai.fishqi.entity.Customer;
import lombok.Data;

@Data
public class FishQSetDTO {
    private Long set_id;
    private String title;
    private String language;
    private Boolean visibility;
    private Customer owner;
    private String description;

}
