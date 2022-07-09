package com.db.fileserver.service;

import com.db.fileserver.entity.User;
import com.db.fileserver.entity.UserFile;
import com.db.fileserver.repo.UserFileRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    private Environment environment;

    private final UserFileRepo userFileRepo;
    private final UserService userService;

    public FileUploadService(UserFileRepo userFileRepo, UserService userService) {
        this.userFileRepo = userFileRepo;
        this.userService = userService;
    }

    @Value("${app.uploadDir}")
    private String fileUploadDir;

    public void uploadFile(List<MultipartFile> multipartFiles, String username) throws IOException {
        List<UserFile> files = new ArrayList<>();

        User uploader = userService.findByUsername(username);

        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            UserFile file = new UserFile();
            file.setFilePath(fileUploadDir + File.separator + multipartFile.getOriginalFilename());
            file.setFileName(fileName);
            file.setFileExtension(fileName.substring(fileName.lastIndexOf('.')));
            file.setUploader(uploader);

            copyFileToLocation(multipartFile, file.getFilePath());

            files.add(file);
        }

        userFileRepo.saveAll(files);
    }

    private void copyFileToLocation(MultipartFile multipartFile, String filePath) throws IOException {
        File target = new File(filePath);
        if (!target.getParentFile().exists()) {
            if (!target.getParentFile().mkdirs()) {
                throw new IOException("Unable to copy. Failed to create target file directory. " + target.getParentFile());
            }
        }
        if(target.exists()){
            throw new FileAlreadyExistsException(environment.getProperty("file.upload.fail.exists"));
        }
        byte[] bytesOfFile = multipartFile.getBytes();
        Path path = Paths.get(filePath);
        log.info("Copying file to {}", path.toAbsolutePath());
        Files.write(path, bytesOfFile);
    }

    public List<UserFile> findByUsername(String username) {
        User uploader = userService.findByUsername(username);
        return uploader.getUserFiles();
    }
}
