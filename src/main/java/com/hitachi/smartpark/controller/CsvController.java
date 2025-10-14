package com.hitachi.smartpark.controller;

import com.hitachi.smartpark.service.impl.CsvService;
import com.hitachi.smartpark.service.model.UploadResponse;
import com.hitachi.smartpark.utils.JobStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/API")
public class CsvController {

    private final CsvService csvService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                UUID jobId = csvService.handleFileUpload(file);
                return ResponseEntity.ok(new UploadResponse(jobId));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            }
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid UUID format"));
        }

        if (!csvService.isValidJobId(uuid)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid ID"));
        }

        JobStatus status = csvService.getJobStatus(uuid);
        if (status == JobStatus.IN_PROGRESS) {
            return ResponseEntity.status(HttpStatus.LOCKED).body(Map.of("error", "Job is still processing"));
        }

        File csvFile = csvService.getCsvFile(uuid);
        if (!csvFile.exists()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Processed file not found"));
        }

        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(csvFile));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to read file"));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + id + ".csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
