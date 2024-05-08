package com.ztpai.fishqi.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
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

        file.setFtpPath(requestFile.getFtpPath());

        this.filesRepository.save(file);

        return "działa";
    }

    public List<Files> saveFile(FilesDTO requestFile, String ownerEmail) throws IOException {
        MultipartFile photo = requestFile.getPhoto();
        List<FishQData> fishQDataList = requestFile.getFishQDataList(this.objectMapper);
        Long ownerId = this.customerRepository.findByEmail(ownerEmail).getUser_id();
        String ftpPath = "FISHQI/" + Long.toString(ownerId) + "/" + Long.toString(requestFile.getSetId()) + "/" + "photo.png";
        
        Files file = new Files(ftpPath);
        this.filesRepository.save(file);

        this.ftpUploader.uploadFile(photo.getInputStream(), ftpPath);

        JSONObject jsonObject = new JSONObject();
        ftpPath = "FISHQI/" + ownerId + "/" + requestFile.getSetId() + "/" + "words.json";
        
        for (FishQData fishQData : fishQDataList) {
            jsonObject.put(fishQData.getWord(), fishQData.getTranslation());
        }

        InputStream inputStream = new ByteArrayInputStream(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
        this.ftpUploader.uploadFile(inputStream, ftpPath);
        
        Files file2 = new Files(ftpPath);
        this.filesRepository.save(file2);


        return List.of(file, file2);
    }

    public void deleteFile(Long fileId) {
        this.filesRepository.deleteById(fileId);
    }

}

// 