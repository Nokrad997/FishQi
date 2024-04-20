package com.ztpai.fishqi.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.ztpai.fishqi.jsonViews.Views;
import com.ztpai.fishqi.validators.interfaces.PasswordMatches;
import com.ztpai.fishqi.validators.interfaces.ValidEmail;
import com.ztpai.fishqi.validators.interfaces.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@PasswordMatches
public class CustomerDTO {
    @JsonView(Views.Public.class)
    private Long user_id;

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
    
    @JsonView(Views.Internal.class)
    @NotBlank(message = "matchingPassword is mandatory")
    private String matchingPassword;
    
    @JsonView(Views.Public.class)
    @NotNull(message = "Is admin is mandatory")
    private Boolean is_admin;

}
