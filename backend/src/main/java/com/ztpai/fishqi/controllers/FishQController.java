package com.ztpai.fishqi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztpai.fishqi.FishqiApplication;
import com.ztpai.fishqi.DTO.FishQDTO;
import com.ztpai.fishqi.services.FishQService;

@RestController
@RequestMapping("/fishq")
public class FishQController {
    private FishQService fishqService;

    public FishQController(FishQService fishqService) {
        this.fishqService = fishqService;
    }

    @GetMapping(value = "/{fishQId}", produces = "application/json")
    public ResponseEntity<?> retrieve(@PathVariable Long fishQId) {
        try {
            return ResponseEntity.ok(this.fishqService.getFishQByID(fishQId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> retrieveAll() {
        try {
            return ResponseEntity.ok(this.fishqService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{fishQId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable Long fishQId, @RequestBody FishQDTO fishQ) {
        try {
            return ResponseEntity.ok(this.fishqService.updateFishQ(fishQ, fishQId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody FishQDTO fishQ) {
        try {
            return ResponseEntity.ok(this.fishqService.createFishQ(fishQ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{fishQId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable Long fishQId) {
        try {
            return ResponseEntity.ok(this.fishqService.deleteFishQ(fishQId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
