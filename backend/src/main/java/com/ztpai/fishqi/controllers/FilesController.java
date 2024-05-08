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

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/files")
public class FilesController {
    private FilesService filesService;

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
}
