package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.FishQSetDTO;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.repositories.FishQSetRepository;

@Service
public class FishQSetService {
    private FishQSetRepository fishQSetRepository;
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    public FishQSetService(FishQSetRepository fishQSetRepository) {
        this.fishQSetRepository =  fishQSetRepository;
    }

    public FishQSetDTO getFishQSetByID(Long fishQId){
        Optional<FishQSet> optFi = this.fishQSetRepository.findById(fishQId);
        FishQSet fishQ = optFi.get();

        if(fishQ == null) {
            throw new IllegalArgumentException("No user with id " + fishQId);
        }

        return convertToDTO(fishQ);
    }

    public List<FishQSetDTO> getAllFishQSet(){
        List<FishQSet> fishQ = this.fishQSetRepository.findAll();
        List<FishQSetDTO> fishQDTOs = fishQ.stream().map(this::convertToDTO).toList();

        return fishQDTOs;
    }

    public String updateFishQSet(FishQSet requestFishQ, Long fishQId) {
        Optional<FishQSet> OptFi = this.fishQSetRepository.findById(fishQId);   
        FishQSet fishQ = OptFi.get(); 

        if(fishQ == null) {
            throw new IllegalArgumentException("No user with id " + fishQId);
        }

        fishQ.setTitle(requestFishQ.getTitle());
        fishQ.setLanguage(requestFishQ.getLanguage());
        fishQ.setVisibility(requestFishQ.getVisibility());
        fishQ.setOwner(requestFishQ.getOwner());
        fishQ.setDescription(requestFishQ.getDescription());
        

        this.fishQSetRepository.save(fishQ);

        return "działa";
    }

    public String saveFishQSet(FishQSet requestFishQSet) {
        // customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        this.fishQSetRepository.save(requestFishQSet);

        return "działa";
    }

    public void deleteFishQSet(Long fishqSetId){
        this.fishQSetRepository.deleteById(fishqSetId);
    }

    private FishQSetDTO convertToDTO(FishQSet ffishQSet) {
        FishQSetDTO dto = new FishQSetDTO();
        dto.setSet_id(ffishQSet.getSet_id());
        dto.setTitle(ffishQSet.getTitle());
        dto.setLanguage(ffishQSet.getLanguage());
        dto.setVisibility(ffishQSet.getVisibility());
        dto.setOwner(ffishQSet.getOwner());
        dto.setDescription(ffishQSet.getDescription());

        return dto;
    }
}
