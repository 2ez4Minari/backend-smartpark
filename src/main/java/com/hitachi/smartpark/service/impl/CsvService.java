package com.hitachi.smartpark.service.impl;

import com.hitachi.smartpark.utils.JobStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

@Service
public class CsvService {

    private static final Path STORAGE_DIR = Paths.get("uploaded-csvs");
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    );

    private final Map<UUID, JobStatus> jobStatusMap = new ConcurrentHashMap<>();

    public CsvService() throws IOException {
        if (!Files.exists(STORAGE_DIR)) {
            Files.createDirectories(STORAGE_DIR);
        }
    }

    public UUID handleFileUpload(MultipartFile file) throws IOException {
        UUID jobId = UUID.randomUUID();
        jobStatusMap.put(jobId, JobStatus.IN_PROGRESS);

        new Thread(() -> {
            try {
                processCsv(file, jobId);
                jobStatusMap.put(jobId, JobStatus.COMPLETED);
            } catch (Exception e) {
                jobStatusMap.remove(jobId);
                e.printStackTrace();
            }
        }).start();

        return jobId;
    }

    private void processCsv(MultipartFile file, UUID jobId) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String header = reader.readLine();
            if (header == null) return;

            lines.add(header + ",hasEmail");

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                boolean hasEmail = EMAIL_REGEX.matcher(line).find();
                lines.add(line + "," + hasEmail);
            }
        }

        Path outputFile = STORAGE_DIR.resolve(jobId.toString() + ".csv");
        Files.write(outputFile, lines, StandardOpenOption.CREATE);
    }

    public JobStatus getJobStatus(UUID id) {
        return jobStatusMap.get(id);
    }

    public File getCsvFile(UUID id) {
        Path filePath = STORAGE_DIR.resolve(id.toString() + ".csv");
        return filePath.toFile();
    }

    public boolean isValidJobId(UUID id) {
        return jobStatusMap.containsKey(id);
    }
}
