package com.ztpai.fishqi.DTO;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long user_id;
    private String email;
    private String username;
    private Boolean is_admin;

}
