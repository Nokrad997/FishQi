package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.FishQSetDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.entity.FishQa;
import com.ztpai.fishqi.entity.Rating;
import com.ztpai.fishqi.repositories.CustomerRepository;
import com.ztpai.fishqi.repositories.FilesRepository;
import com.ztpai.fishqi.repositories.FishQRepository;
import com.ztpai.fishqi.repositories.FishQSetRepository;
import com.ztpai.fishqi.repositories.RatingRepository;

import jakarta.transaction.Transactional;

@Service
public class FishQSetService {
    private FishQSetRepository fishQSetRepository;
    private CustomerRepository customerRepository;
    private FilesRepository filesRepository;
    private FishQRepository fishQRepository;
    private RatingRepository ratingRepository;
    private FTPUploader ftpUploader;

    public FishQSetService(FishQSetRepository fishQSetRepository,
            CustomerRepository customerRepository, FilesRepository filesRepository, FishQRepository fishQRepository,
            RatingRepository ratingRepository, FTPUploader ftpUploader) {
        this.fishQSetRepository = fishQSetRepository;
        this.customerRepository = customerRepository;
        this.filesRepository = filesRepository;
        this.fishQRepository = fishQRepository;
        this.ratingRepository = ratingRepository;
        this.ftpUploader = ftpUploader;
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
            file = this.filesRepository.findByFtpPath(requestFishQ.getFtpImagePath());
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

    @Transactional
    public void deleteFishQSet(Long fishqSetId) {
        FishQSet fishQset = this.fishQSetRepository.findById(fishqSetId).orElseThrow();
        FishQa fishq = this.fishQRepository.findById(fishqSetId).orElseThrow();
        Files wordsToDelete = fishq.getFile();
        Files imageToDelete = fishQset.getImage();
        List<Rating> ratingsToDelete = this.ratingRepository.findAllByFishQSet(fishQset);
        for (Rating rating : ratingsToDelete) {
            System.out.println(rating);
        }
        this.ratingRepository.deleteAll(ratingsToDelete);
        this.fishQRepository.delete(fishq);
        fishQset.setImage(null);
        this.fishQSetRepository.save(fishQset);
        this.filesRepository.delete(wordsToDelete);
        if(imageToDelete != null) this.filesRepository.delete(imageToDelete);
        if(wordsToDelete != null) this.filesRepository.delete(wordsToDelete);

        this.ftpUploader.deleteSetCatalog(fishQset.getOwner().getUserId(), fishqSetId);

        this.fishQSetRepository.delete(fishQset);
    }
}
