package com.ztpai.fishqi.DTO;

import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.entity.Rating;

import lombok.Data;

@Data
public class RatingDTO {
    private Long ratingId;
    private FishQSet fishQSet;
    private Customer customer;
    private Integer score;

    public Rating convertToEntity() {
        Rating rating = new Rating();
        rating.setRatingId(this.ratingId);
        rating.setFishQSet(this.fishQSet);
        rating.setCustomer(this.customer);
        rating.setScore(this.score);

        return rating;
    }
}
