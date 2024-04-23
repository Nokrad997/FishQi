package com.ztpai.fishqi.DTO;
import com.fasterxml.jackson.annotation.JsonView;
import com.ztpai.fishqi.jsonViews.Views;

import lombok.Data;

@Data
public class RefreshDTO {
    @JsonView(Views.Internal.class)
    private String refreshToken;
    
    @JsonView(Views.Public.class)
    private String accessToken;

    public RefreshDTO(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
