package com.ztpai.fishqi.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.ztpai.fishqi.jsonViews.Views;
import com.ztpai.fishqi.validators.interfaces.PasswordMatches;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
@PasswordMatches
public class RegistrationDTO extends CustomerDTO {
    @JsonView(Views.Internal.class)
    @NotBlank(message = "matchingPassword is mandatory")
    private String matchingPassword;
    
    @JsonView(Views.Public.class)
    private Boolean is_admin = false;
}
