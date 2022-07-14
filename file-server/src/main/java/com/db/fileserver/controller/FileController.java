package com.db.fileserver.controller;

import com.db.fileserver.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * @author Sanjeev on 14-07-2022
 * @Project: file-server
 */

@Slf4j
@RestController
public class FileController {

	private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    private Environment environment;
	
	@PostMapping("/files")
    public ResponseEntity<?> uploadFile(@RequestParam("doc") MultipartFile[] files, String comment) throws IOException, ExecutionException, InterruptedException {
        String message = "";
        for (MultipartFile file : files) {
            message += "Name: %s, original name: %s, content-type: %s, size: %dB%n".formatted(file.getName(), file.getOriginalFilename(), file.getContentType(), file.getSize());
            log.info(message);
        }
        fileService.storeFile(Arrays.asList(files), comment);
    
        return ResponseEntity.ok(environment.getProperty("file.upload.success"));
    }
}
