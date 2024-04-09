package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.FilesDTO;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.repositories.FilesRepository;

@Service
public class FilesService {
    private FilesRepository filesRepository;
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public FilesDTO getFileByID(Long fileId){
        Optional<Files> optFi = this.filesRepository.findById(fileId);
        Files file = optFi.get();

        if(file == null) {
            throw new IllegalArgumentException("No user with id " + fileId);
        }

        return convertToDTO(file);
    }

    public List<FilesDTO> getAllFiles(){
        List<Files> files = this.filesRepository.findAll();
        List<FilesDTO> filesDTOs = files.stream().map(this::convertToDTO).toList();

        return filesDTOs;
    }

    public String updateFile(Files requestFile, Long fileId) {
        Optional<Files> OptFi = this.filesRepository.findById(fileId);   
        Files file = OptFi.get(); 

        if(file == null) {
            throw new IllegalArgumentException("No user with id " + fileId);
        }

        file.setFtp_path(requestFile.getFtp_path());
        

        this.filesRepository.save(file);

        return "działa";
    }

    public String saveFile(Files requestFile) {
        // customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        this.filesRepository.save(requestFile);

        return "działa";
    }

    public void deleteFile(Long fileId){
        this.filesRepository.deleteById(fileId);
    }

    private FilesDTO convertToDTO(Files file) {
        FilesDTO dto = new FilesDTO();
        dto.setFile_id(file.getFile_id());
        dto.setFtp_path(file.getFtp_path());

        return dto;
    }
}
