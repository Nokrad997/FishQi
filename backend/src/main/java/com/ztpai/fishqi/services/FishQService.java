package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.FishQDTO;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.entity.FishQa;
import com.ztpai.fishqi.repositories.FilesRepository;
import com.ztpai.fishqi.repositories.FishQRepository;
import com.ztpai.fishqi.repositories.FishQSetRepository;

@Service
public class FishQService {
    private FishQRepository fishQRepository;
    private FishQSetRepository fishQSetRepository;
    private FilesRepository filesRepository;

    public FishQService(FishQRepository fishQRepository, FishQSetRepository fishQSetRepository,
            FilesRepository filesRepository) {
        this.fishQRepository = fishQRepository;
        this.fishQSetRepository = fishQSetRepository;
        this.filesRepository = filesRepository;
    }

    public FishQDTO getFishQByID(Long fishQId) {
        Optional<FishQa> optFi = this.fishQRepository.findById(fishQId);
        FishQa fishQ = optFi.get();

        if (fishQ == null) {
            throw new IllegalArgumentException("No user with id " + fishQId);
        }

        return fishQ.convertToDTO();
    }

    public List<FishQDTO> getAll() {
        List<FishQa> fishQ = this.fishQRepository.findAll();
        List<FishQDTO> fishQDTOs = fishQ.stream().map(FishQa::convertToDTO).toList();

        return fishQDTOs;
    }

    public FishQDTO updateFishQ(FishQDTO requestFishQ, Long fishQId) {
        Optional<FishQa> OptFi = this.fishQRepository.findById(fishQId);
        FishQa fishQ = OptFi.get();

        if (fishQ == null) {
            throw new IllegalArgumentException("No set with id " + fishQId);
        }

        if (requestFishQ.getSetId() != null) {
            Optional<FishQSet> optSet = this.fishQSetRepository.findById(requestFishQ.getSetId());
            FishQSet set = optSet.get();
            fishQ.setSet(set);
        }

        if (requestFishQ.getFtpWordsPath() != null) {
            fishQ.getFile().setFtpPath(requestFishQ.getFtpWordsPath());
        }

        this.fishQRepository.save(fishQ);

        return fishQ.convertToDTO();
    }

    public FishQDTO createFishQ(FishQDTO requestFishQ) {
        FishQa fishQ = new FishQa();
        Optional<FishQSet> optSet = this.fishQSetRepository.findById(requestFishQ.getSetId());
        FishQSet set = optSet.get();
        fishQ.setSet(set);

        if (this.filesRepository.findByFtpPath(requestFishQ.getFtpWordsPath()) != null) {
            fishQ.setFile(this.filesRepository.findByFtpPath(requestFishQ.getFtpWordsPath()));
        } else {
            throw new IllegalArgumentException("No file with path " + requestFishQ.getFtpWordsPath());
        }

        this.fishQRepository.save(fishQ);

        return fishQ.convertToDTO();
    }

    public FishQDTO deleteFishQ(Long fishQId) {
        Optional<FishQa> optFi = this.fishQRepository.findById(fishQId);
        FishQa fishQ = optFi.get();

        if (fishQ == null) {
            throw new IllegalArgumentException("No set with id " + fishQId);
        }

        this.fishQRepository.delete(fishQ);

        return fishQ.convertToDTO();
    }
}
