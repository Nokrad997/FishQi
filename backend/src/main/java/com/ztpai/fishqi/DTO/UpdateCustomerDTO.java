package com.ztpai.fishqi.DTO;
import lombok.Data;

@Data
public class UpdateCustomerDTO {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Boolean is_admin;
}
