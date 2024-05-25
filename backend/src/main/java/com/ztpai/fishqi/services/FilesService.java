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
        Long ownerId = this.customerRepository.findByEmail(ownerEmail).getUserId();
        String ftpPath = "FISHQI/" + Long.toString(ownerId) + "/" + Long.toString(requestFile.getSetId()) + "/"
        + "photo.png";
        Files file = new Files();

        if (photo != null) {
            file.setFtpPath(ftpPath);
            this.filesRepository.save(file);

            this.ftpUploader.uploadFile(photo.getInputStream(), ftpPath);
        }

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

    public String getFile(String filePath) throws IOException {
        String localPath = "/";
        this.ftpUploader.downloadFile(filePath, localPath);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        return localPath + fileName;
    }    

    public String updateFiles(FilesDTO files, String ownerEmail) throws IOException {
        try {
            MultipartFile photo = files.getPhoto();
            List<FishQData> fishQDataList = files.getFishQDataList(this.objectMapper);
            Long ownerId = this.customerRepository.findByEmail(ownerEmail).getUserId();
            String ftpPath = "FISHQI/" + Long.toString(ownerId) + "/" + Long.toString(files.getSetId()) + "/"
            + "photo.png";
            Files file = this.filesRepository.findByFtpPath(ftpPath);
    
            if (photo != null) {
                if (file == null) {
                    file = new Files();
                    file.setFtpPath(ftpPath);
                    this.filesRepository.save(file);
                } else {
                    file.setFtpPath(ftpPath);
                    this.filesRepository.save(file);
                }

                this.ftpUploader.uploadFile(photo.getInputStream(), ftpPath);
            }
    
            JSONObject jsonObject = new JSONObject();
            ftpPath = "FISHQI/" + ownerId + "/" + files.getSetId() + "/" + "words.json";
    
            for (FishQData fishQData : fishQDataList) {
                jsonObject.put(fishQData.getWord(), fishQData.getTranslation());
            }
    
            InputStream inputStream = new ByteArrayInputStream(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
            this.ftpUploader.uploadFile(inputStream, ftpPath);

            Files file2 = this.filesRepository.findByFtpPath(ftpPath);
            if (file2 == null) {
                file2 = new Files(ftpPath);
                this.filesRepository.save(file2);
            } else {
                file2.setFtpPath(ftpPath);
                this.filesRepository.save(file2);
            }
    
            return "nie wyjebao sie";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

//