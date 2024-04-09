package com.ztpai.fishqi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.ztpai.fishqi.services.FishQSetService;

@RestController
@RequestMapping("/fishqset")
public class FishQSetController {
    private FishQSetService fishQSetService;

    public FishQSetController (FishQSetService fishQSetService) {
        this.fishQSetService = fishQSetService;
    }

    @GetMapping(value = "/{fishQSetId}",produces = "application/json")
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
    public String update(@PathVariable Long fishQSetId, @RequestBody FishQSet fishQSet) {
        this.fishQSetService.updateFishQSet(fishQSet, fishQSetId);
        return "File updated";
    }
    
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody FishQSet fishQSet) {
        this.fishQSetService.saveFishQSet(fishQSet);
        return "File created";
    }

    @DeleteMapping(value="/{fishQSetId}", produces = "application/json")
    public String delete(@PathVariable Long fishQSetId) {
        this.fishQSetService.deleteFishQSet(fishQSetId);
        return "file deleted";
    }
}
