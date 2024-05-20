package com.ztpai.fishqi.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.jsonViews.Views;
import com.ztpai.fishqi.validators.interfaces.ValidEmail;
import com.ztpai.fishqi.validators.interfaces.ValidPassword;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CustomerDTO {
    @JsonView(Views.Public.class)
    private Long userId;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Email is mandatory")
    @ValidEmail
    private String email;
    
    @JsonView(Views.Public.class)
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @JsonView(Views.Internal.class)
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;
    
    @JsonView(Views.Public.class)
    private Boolean is_admin;

    public Customer convertToEntity() {
        Customer customer = new Customer();
        customer.setEmail(this.getEmail());
        customer.setUsername(this.getUsername());
        customer.setPassword(this.getPassword());
        customer.setIs_admin(this.getIs_admin());

        return customer;
    }
}
