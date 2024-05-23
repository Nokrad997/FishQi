package com.ztpai.fishqi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ztpai.fishqi.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByFishQSetSetIdAndCustomerUserId(Long fishQSetSetId, Long customerUserId);

}
