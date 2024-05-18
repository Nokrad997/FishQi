package com.ztpai.fishqi.entity;

import com.ztpai.fishqi.DTO.RatingDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private FishQSet fishQSet;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Integer score;

    public Rating() {}

    public RatingDTO convertToDTO() {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRatingId(this.ratingId);
        ratingDTO.setFishQSet(this.fishQSet);
        ratingDTO.setCustomer(this.customer);
        ratingDTO.setScore(this.score);

        return ratingDTO;
    }
}
