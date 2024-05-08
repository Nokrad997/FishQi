package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.FishQSetDTO;
import com.ztpai.fishqi.config.JwtTokenUtil;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.repositories.CustomerRepository;
import com.ztpai.fishqi.repositories.FilesRepository;
import com.ztpai.fishqi.repositories.FishQSetRepository;

@Service
public class FishQSetService {
    private FishQSetRepository fishQSetRepository;
    private CustomerRepository customerRepository;
    private FilesRepository filesRepository;
    private JwtTokenUtil jwtTokenUtil;

    public FishQSetService(FishQSetRepository fishQSetRepository, JwtTokenUtil jwtTokenUtil,
            CustomerRepository customerRepository, FilesRepository filesRepository) {
        this.fishQSetRepository = fishQSetRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.customerRepository = customerRepository;
        this.filesRepository = filesRepository;
    }

    public FishQSetDTO getFishQSetByID(Long fishQId) {
        Optional<FishQSet> optFi = this.fishQSetRepository.findById(fishQId);
        FishQSet fishQ = optFi.get();

        if (fishQ == null) {
            throw new IllegalArgumentException("No user with id " + fishQId);
        }

        return fishQ.convertToDTO();
    }

    public List<FishQSetDTO> getAllFishQSet() {
        List<FishQSet> fishQ = this.fishQSetRepository.findAll();
        List<FishQSetDTO> fishQDTOs = fishQ.stream().map(FishQSet::convertToDTO).toList();

        return fishQDTOs;
    }

    public FishQSetDTO updateFishQSet(FishQSetDTO requestFishQ, Long fishQId) {
        Optional<FishQSet> OptFi = this.fishQSetRepository.findById(fishQId);
        FishQSet fishQ = OptFi.get();

        if (fishQ == null) {
            throw new IllegalArgumentException("No set with id " + fishQId);
        }

        String title = requestFishQ.getTitle() != null ? requestFishQ.getTitle() : fishQ.getTitle();
        String language = requestFishQ.getLanguage() != null ? requestFishQ.getLanguage() : fishQ.getLanguage();
        String visibility = requestFishQ.getVisibility() != null ? requestFishQ.getVisibility() : fishQ.getVisibility();
        Customer owner = requestFishQ.getOwner_id() != null
                ? this.customerRepository.findById(requestFishQ.getOwner_id()).orElseThrow()
                : fishQ.getOwner();
        String description = requestFishQ.getDescription() != null ? requestFishQ.getDescription()
                : fishQ.getDescription();
                
        Files file = fishQ.getImage();
        if (requestFishQ.getFtpImagePath() != null) {
            file = new Files(requestFishQ.getFtpImagePath());
            this.filesRepository.save(file); 
        }

        fishQ.setTitle(title);
        fishQ.setLanguage(language);
        fishQ.setVisibility(visibility);
        fishQ.setOwner(owner);
        fishQ.setDescription(description);
        fishQ.setImage(file);

        this.fishQSetRepository.save(fishQ);

        return fishQ.convertToDTO();
    }

    public FishQSetDTO saveFishQSet(FishQSetDTO requestFishQSet, String name) {
        FishQSet fishQSet = requestFishQSet.convertToEntity();
        Customer cust = this.customerRepository.findByEmail(name);

        fishQSet.setOwner(cust);

        this.fishQSetRepository.save(fishQSet);

        return fishQSet.convertToDTO();
    }

    public void deleteFishQSet(Long fishqSetId) {
        this.fishQSetRepository.deleteById(fishqSetId);
    }
}
