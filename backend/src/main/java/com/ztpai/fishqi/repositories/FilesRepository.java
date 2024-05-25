package com.ztpai.fishqi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ztpai.fishqi.entity.Files;

import jakarta.transaction.Transactional;

public interface FilesRepository extends JpaRepository<Files, Long> {
    public Files findByFtpPath(String ftpPath);

    @Modifying
    @Transactional
    @Query("DELETE FROM Files f WHERE f IN :files")
    void deleteByFtpPathIn(@Param("files") List<Files> files);
}
