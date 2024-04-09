package com.ztpai.fishqi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ztpai.fishqi.DTO.FilesDTO;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.services.FilesService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/files")
public class FilesController {
    private FilesService filesService;

    public FilesController (FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping(value = "/{fileId}",produces = "application/json")
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
    
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody Files file) {
        this.filesService.saveFile(file);
        return "File created";
    }

    @DeleteMapping(value="/{fileId}", produces = "application/json")
    public String delete(@PathVariable Long fileId) {
        this.filesService.deleteFile(fileId);
        return "file deleted";
    }
}
