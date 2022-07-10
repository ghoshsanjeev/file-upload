package com.db.fileserver.controller;

import com.db.fileserver.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author Sanjeev on 09-07-2022
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


    //@PostMapping(value = "/files", consumes = {MediaType.APPLICATION_PDF_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PostMapping("/files")
    public ResponseEntity<?> uploadFiles(@RequestParam("file") MultipartFile[] file) throws IOException, ExecutionException, InterruptedException {

        fileService.storeFile(Arrays.asList(file));

        return ResponseEntity.ok(environment.getProperty("file.upload.success"));
    }


    /*@GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/




}
