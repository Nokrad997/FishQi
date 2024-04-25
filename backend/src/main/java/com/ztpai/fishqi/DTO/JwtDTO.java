package com.ztpai.fishqi.DTO;

import lombok.Data;

@Data
public class JwtDTO {
    private String accessToken;
    private String refreshToken;

    public JwtDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
