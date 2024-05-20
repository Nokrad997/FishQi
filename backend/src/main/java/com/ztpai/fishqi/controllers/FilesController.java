package com.ztpai.fishqi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztpai.fishqi.DTO.FilesDTO;
import com.ztpai.fishqi.DTO.FilesDTO.FishQData;
import com.ztpai.fishqi.DTO.FishQSetDTO;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.services.FilesService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/files")
public class FilesController {
    private FilesService filesService;
    private final Lock lock = new ReentrantLock();

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping(value = "/{fileId}", produces = "application/json")
    public ResponseEntity<?> retrieve(@PathVariable Long fileId) {
        FilesDTO file = filesService.getFileByID(fileId);
        return ResponseEntity.ok(file);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<FilesDTO>> retrieveAll() {
        List<FilesDTO> files = this.filesService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    @PutMapping(value = "/{fileId}", consumes = "application/json", produces = "application/json")
    public String update(@PathVariable Long fileId, @RequestBody Files file) {
        this.filesService.updateFile(file, fileId);
        return "File updated";
    }

    @PostMapping(value = "/", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> store(@ModelAttribute FilesDTO files, Authentication auth) {
        try {
            //
            return ResponseEntity.ok(this.filesService.saveFile(files, auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{fileId}", produces = "application/json")
    public String delete(@PathVariable Long fileId) {
        this.filesService.deleteFile(fileId);
        return "file deleted";
    }

    @PutMapping(value = "/update/{fileId}", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> updateFiles(@ModelAttribute FilesDTO files, @PathVariable Long fileId, Authentication auth) {
        try {
            return ResponseEntity.ok(this.filesService.updateFiles(files, auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/getphoto", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPhoto(@RequestParam String filePath) {
        lock.lock();
        try {
            String downloadedFilePath = this.filesService.getFile(filePath);
            Path path = Paths.get(downloadedFilePath);
            File file = path.toFile();
            byte[] fileContent;

            try (InputStream in = new FileInputStream(file)) {
                fileContent = StreamUtils.copyToByteArray(in);
            }

            java.nio.file.Files.delete(path);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(fileContent.length)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        } finally {
            lock.unlock();
        }
    }

    @GetMapping(value = "/getwords", produces = "application/json")
    public ResponseEntity<?> getWords(@RequestParam String filePath) {
        lock.lock();
        try {
            String downloadedFilePath = this.filesService.getFile(filePath);
            System.out.println(downloadedFilePath);
            Path path = Paths.get(downloadedFilePath);
            File file = path.toFile();
            String jsonData;

            try (InputStream in = new FileInputStream(file)) {
                jsonData = new String(StreamUtils.copyToByteArray(in), StandardCharsets.UTF_8);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonObject = objectMapper.readValue(jsonData, Object.class);
            System.out.println(jsonObject);
            java.nio.file.Files.delete(path);

            return ResponseEntity.ok(jsonObject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
