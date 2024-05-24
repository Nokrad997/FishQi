package com.ztpai.fishqi.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ztpai.fishqi.entity.Files;

import jakarta.transaction.Transactional;

public interface FilesRepository extends JpaRepository<Files, Long> {
    public Files findByFtpPath(String ftpPath);

    @Modifying
    @Query("DELETE FROM Files f WHERE f.fileId IN :ids")
    void deleteAllByIdIn(@Param("ids") Set<Long> ids);
}
