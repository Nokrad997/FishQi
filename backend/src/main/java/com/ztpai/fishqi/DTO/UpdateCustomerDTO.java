package com.ztpai.fishqi.DTO;
import lombok.Data;

@Data
public class UpdateCustomerDTO {
    private Long user_id;
    private String username;
    private String email;
    private String password;
    private Boolean is_admin;
}
