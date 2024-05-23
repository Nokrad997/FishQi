package com.ztpai.fishqi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RatingRequestDTO {
    @JsonProperty("fishQSetId")
    private Long fishQSetId;

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("score")
    private Integer score;

    public RatingRequestDTO() {
    }
}
