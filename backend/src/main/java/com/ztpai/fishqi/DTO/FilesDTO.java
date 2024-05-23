package com.ztpai.fishqi.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztpai.fishqi.entity.Files;

import io.jsonwebtoken.io.IOException;
import lombok.Data;

@Data
public class FilesDTO {
    private Long fileId;
    private Long setId;
    private String fishqs;

    @JsonIgnore
    private MultipartFile photo;
    private String ftpPhotoPath;
    private String ftpPath;

    public Files convertToEntity() {
        Files files = new Files();
        files.setFileId(this.fileId);
        files.setFtpPath(this.ftpPath);
        return files;
    }

    public List<FishQData> getFishQDataList(ObjectMapper objectMapper) throws IOException {
        try {
            return objectMapper.readValue(this.fishqs,
                    new com.fasterxml.jackson.core.type.TypeReference<List<FishQData>>() {
                    });
        } catch (Exception e) {
            throw new IOException("Error parsing fishqs");
        }
    }

    public static class FishQData {
        private Long key;
        private String word;
        private String translation;

        public Long getKey() {
            return key;
        }

        public void setKey(Long key) {
            this.key = key;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }
}
