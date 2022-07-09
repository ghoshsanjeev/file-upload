package com.db.fileserver.controller;

import com.db.fileserver.service.FileUploadService;
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

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

@Slf4j
@RestController
public class FileController {

    private FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Autowired
    private Environment environment;


    //@PostMapping(value = "/files", consumes = {MediaType.APPLICATION_PDF_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PostMapping("/files")
    public ResponseEntity uploadFiles(@RequestParam("file") MultipartFile file) throws IOException {

        Optional<String> optionalUsername = getLoggedInUsername();
        if (optionalUsername.isEmpty()) {
            throw new UsernameNotFoundException(environment.getProperty("user.not.found"));
        }

        fileUploadService.uploadFile(Arrays.asList(file), optionalUsername.get());

        return ResponseEntity.ok(environment.getProperty("file.upload.success"));
    }


    /*@GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/


    private Optional<String> getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return Optional.ofNullable(username);
    }

}
