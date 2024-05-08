package com.ztpai.fishqi.controllers;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztpai.fishqi.DTO.FishQSetDTO;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.services.CustomerService;
import com.ztpai.fishqi.services.FishQSetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fishqset")
public class FishQSetController {
    private FishQSetService fishQSetService;
    private CustomerService customerService;

    public FishQSetController(FishQSetService fishQSetService, CustomerService customerService) {
        this.fishQSetService = fishQSetService;
        this.customerService = customerService;
    }

    @GetMapping(value = "/{fishQSetId}", produces = "application/json")
    public ResponseEntity<?> retrieve(@PathVariable Long fishQSetId) {
        FishQSetDTO fishQSet = fishQSetService.getFishQSetByID(fishQSetId);
        return ResponseEntity.ok(fishQSet);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<FishQSetDTO>> retrieveAll() {
        List<FishQSetDTO> fishQSet = this.fishQSetService.getAllFishQSet();
        return ResponseEntity.ok(fishQSet);
    }

    @PutMapping(value = "/{fishQSetId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable Long fishQSetId, @RequestBody FishQSetDTO fishQSet, Authentication auth) {
        try {
            if(!this.customerService.checkIfAdmin(auth.getName())) {
                Long setOwnerId = this.fishQSetService.getFishQSetByID(fishQSetId).getOwner_id();
                if (!auth.getName().equals(this.customerService.getCustomerByID(setOwnerId).getEmail())) {
            
                    return ResponseEntity.badRequest().body("You can't retrieve other users data");
                }
            }

            return ResponseEntity.ok(this.fishQSetService.updateFishQSet(fishQSet, fishQSetId));
        } catch (Exception e) {
            
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> store(@Valid @RequestBody FishQSetDTO fishQSet, Authentication auth) {
        try {
            return ResponseEntity.ok(this.fishQSetService.saveFishQSet(fishQSet, auth.getName()));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/{fishQSetId}", produces = "application/json")
    public String delete(@PathVariable Long fishQSetId) {
        this.fishQSetService.deleteFishQSet(fishQSetId);
        return "file deleted";
    }
}
