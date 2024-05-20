package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.RatingDTO;
import com.ztpai.fishqi.DTO.RatingRequestDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.entity.Rating;
import com.ztpai.fishqi.repositories.CustomerRepository;
import com.ztpai.fishqi.repositories.FishQSetRepository;
import com.ztpai.fishqi.repositories.RatingRepository;

@Service
public class RatingService {
    private RatingRepository ratingRepository;
    private FishQSetRepository fishQSetRepository;
    private CustomerRepository customerRepository;

    public RatingService(RatingRepository ratingRepository, FishQSetRepository fishQSetRepository, CustomerRepository customerRepository) {
        this.ratingRepository = ratingRepository;
        this.fishQSetRepository = fishQSetRepository;
        this.customerRepository = customerRepository;
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

    public RatingDTO createRating(RatingRequestDTO ratingDTO) {
        FishQSet fishQSet = this.fishQSetRepository.findById(ratingDTO.getFishQSetId()).get();
        if (fishQSet == null) {
            throw new IllegalArgumentException("No fishQSet with id " + ratingDTO.getFishQSetId());
        }
        Customer cust = this.customerRepository.findById(ratingDTO.getCustomerId()).get();
        if (cust == null) {
            throw new IllegalArgumentException("No customer with id " + ratingDTO.getCustomerId());
        }
        Rating rating = new Rating(fishQSet, cust, ratingDTO.getScore());
        this.ratingRepository.save(rating);

        return rating.convertToDTO();
    }

    public RatingDTO updateRating(RatingRequestDTO ratingDTO) {
        Rating rating = this.ratingRepository.findByFishQSetSetIdAndCustomerUserId(ratingDTO.getFishQSetId(), ratingDTO.getCustomerId());
        if (rating == null) {
            throw new IllegalArgumentException("No rating with fishQSetId " + ratingDTO.getFishQSetId() + " and customerId " + ratingDTO.getCustomerId());
        }

        rating.setScore(ratingDTO.getScore());
        this.ratingRepository.save(rating);

        return rating.convertToDTO();
    }
}
