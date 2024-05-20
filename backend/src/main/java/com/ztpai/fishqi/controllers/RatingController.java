package com.ztpai.fishqi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztpai.fishqi.DTO.RatingDTO;
import com.ztpai.fishqi.DTO.RatingRequestDTO;
import com.ztpai.fishqi.services.RatingService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/{ratingId}", produces = "application/json")
    public ResponseEntity<?> retrieve(@PathVariable Long ratingId) {
        try{
            return ResponseEntity.ok(ratingService.getRatingByID(ratingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> retrieveAll() {
        try{
            return ResponseEntity.ok(ratingService.getAllRatings());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody RatingRequestDTO ratingDTO) {
        try{
            return ResponseEntity.ok(this.ratingService.createRating(ratingDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping(value = "/", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody RatingRequestDTO ratingDTO) {
        try{
            return ResponseEntity.ok(this.ratingService.updateRating(ratingDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
