package com.ztpai.fishqi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ztpai.fishqi.entity.Files;

public interface FilesRepository extends JpaRepository<Files, Long>{
    public Files findByFtpPath(String ftpPath);
    
}
