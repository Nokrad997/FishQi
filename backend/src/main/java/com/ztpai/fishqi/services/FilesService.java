package com.ztpai.fishqi.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztpai.fishqi.DTO.FilesDTO;
import com.ztpai.fishqi.DTO.FilesDTO.FishQData;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.repositories.CustomerRepository;
import com.ztpai.fishqi.repositories.FilesRepository;

@Service
public class FilesService {
    private FilesRepository filesRepository;
    private CustomerRepository customerRepository;
    private FTPUploader ftpUploader;
    private final ObjectMapper objectMapper;

    public FilesService(FilesRepository filesRepository, CustomerRepository customerRepository,
            FTPUploader ftpUploader, ObjectMapper objectMapper) {
        this.filesRepository = filesRepository;
        this.customerRepository = customerRepository;
        this.ftpUploader = ftpUploader;
        this.objectMapper = objectMapper;
    }

    public FilesDTO getFileByID(Long fileId) {
        Optional<Files> optFi = this.filesRepository.findById(fileId);
        Files file = optFi.get();

        if (file == null) {
            throw new IllegalArgumentException("No user with id " + fileId);
        }

        return file.convertToDTO();
    }

    public List<FilesDTO> getAllFiles() {
        List<Files> files = this.filesRepository.findAll();
        List<FilesDTO> filesDTOs = files.stream().map(Files::convertToDTO).toList();

        return filesDTOs;
    }

    public String updateFile(Files requestFile, Long fileId) {
        Optional<Files> OptFi = this.filesRepository.findById(fileId);
        Files file = OptFi.get();

        if (file == null) {
            throw new IllegalArgumentException("No file with id " + fileId);
        }

        file.setFtp_path(requestFile.getFtp_path());

        this.filesRepository.save(file);

        return "działa";
    }

    public String saveFile(FilesDTO requestFile, String ownerEmail) throws IOException {
        MultipartFile photo = requestFile.getPhoto();
        List<FishQData> fishQDataList = requestFile.getFishQDataList(this.objectMapper);
        Long ownerId = this.customerRepository.findByEmail(ownerEmail).getUser_id();
        String ftpPath = "FISHQI/" + Long.toString(ownerId) + "/" + Long.toString(requestFile.getSet_id()) + "/" + "photo.png";

        this.ftpUploader.uploadFile(photo.getInputStream(), ftpPath);
        return "działa";
    }

    public void deleteFile(Long fileId) {
        this.filesRepository.deleteById(fileId);
    }

}

// 