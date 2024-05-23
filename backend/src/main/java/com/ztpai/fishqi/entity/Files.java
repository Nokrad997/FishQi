package com.ztpai.fishqi.entity;

import com.ztpai.fishqi.DTO.FilesDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(name = "ftp_path", nullable = false)  // This maps to the actual database column name
    private String ftpPath;  // Use camelCase here

    public Files() {}

    public Files(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public FilesDTO convertToDTO() {
        FilesDTO filesDTO = new FilesDTO();
        filesDTO.setFileId(this.fileId);
        filesDTO.setFtpPath(this.ftpPath);
        return filesDTO;
    }
}

