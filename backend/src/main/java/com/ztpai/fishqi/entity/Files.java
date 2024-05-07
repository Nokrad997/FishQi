package com.ztpai.fishqi.entity;

import com.ztpai.fishqi.DTO.FilesDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Files {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;

    @Column(nullable = false)
    private String ftp_path;

    public Files() {}

    public Files(String ftp_path) {
        this.ftp_path = ftp_path;
    }

    public FilesDTO convertToDTO() {
        FilesDTO filesDTO = new FilesDTO();
        filesDTO.setFile_id(this.file_id);
        filesDTO.setFtp_path(this.ftp_path);
        return filesDTO;
    }
}
