package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.RatingDTO;
import com.ztpai.fishqi.entity.Rating;
import com.ztpai.fishqi.repositories.RatingRepository;

@Service
public class RatingService {
    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public RatingDTO getRatingByID(Long ratingId) {
        Optional<Rating> optRa = this.ratingRepository.findById(ratingId);
        Rating rating = optRa.get();

        if (rating == null) {
            throw new IllegalArgumentException("No rating with id " + ratingId);
        }

        return rating.convertToDTO();
    }

    public List<RatingDTO> getAllRatings() {
        List<Rating> ratings = this.ratingRepository.findAll();
        List<RatingDTO> ratingDTOs = ratings.stream().map(Rating::convertToDTO).toList();

        return ratingDTOs;
    }
}
