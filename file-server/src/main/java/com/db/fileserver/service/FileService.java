package com.db.fileserver.service;

import com.db.fileserver.entity.User;
import com.db.fileserver.entity.UserFile;
import com.db.fileserver.exception.StorageSizeMaxedException;
import com.db.fileserver.repo.UserFileRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

@Slf4j
@Service
public class FileService {

    @Value("${app.uploadDir}")
    private String fileUploadDir;
    @Value("${app.uploadDir.max-size-GB:5}")
    private float maxStorageSizeInGB;

    @Autowired
    private Environment environment;

    private final UserFileRepo userFileRepo;
    private final UserService userService;

    public FileService(UserFileRepo userFileRepo, UserService userService) {
        this.userFileRepo = userFileRepo;
        this.userService = userService;
    }

    private long getFileOrDirSize(String path) {
        long size;
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory())
                size = FileUtils.sizeOfDirectory(file);
            else
                size = FileUtils.sizeOf(file);
        } else {
            size = 0;
        }
        return size;
    }

    public long getFileOrDirSizeInKB(String path) {
        return getFileOrDirSize(path) / 1024;
    }

    public double getFileOrDirSizeInMB(String path) {
        return (double) getFileOrDirSizeInKB(path) / 1024;
    }

    public double getFileOrDirSizeInGB(String path) {
        return getFileOrDirSizeInMB(path) / 1024;
    }

    public double getMaxStorageSizeInMB() {
        return maxStorageSizeInGB * 1024;
    }

    public Future<Double> getUploadStorageSizeInMB() {

        CompletableFuture<Double> calculateSize = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            calculateSize.complete(getFileOrDirSizeInMB(fileUploadDir));
        });

        return calculateSize;
    }

    public void storeFile(List<MultipartFile> multipartFiles, String comment) throws IOException, ExecutionException, InterruptedException {

        Optional<String> optionalUsername = userService.getLoggedInUsername();
        if (optionalUsername.isEmpty()) {
            throw new UsernameNotFoundException(environment.getProperty("user.not.found"));
        }

        List<UserFile> files = new ArrayList<>();

        String username = optionalUsername.get();
        User uploader = userService.findByUsername(username);

        for (MultipartFile multipartFile : multipartFiles) {
            UserFile file = prepareFileForStoring(multipartFile, uploader, comment);
            copyFileToLocation(multipartFile, file.getFilePath());
            files.add(file);
        }

        userFileRepo.saveAll(files);
    }

    private UserFile prepareFileForStoring(MultipartFile multipartFile, User uploader, String comment) throws ExecutionException, InterruptedException {
        Future<Double> storageSizeInMBFuture = getUploadStorageSizeInMB();
        String fileName = multipartFile.getOriginalFilename();
        UserFile file = new UserFile(fileUploadDir + File.separator + multipartFile.getOriginalFilename());
        file.setFileName(fileName);
        file.setFileExtension(FilenameUtils.getExtension(fileName));
        file.setUploader(uploader);
        file.setFileSizeInMB(getMultipartFileSizeInMB(multipartFile));
        file.setComment(comment);
        file.setUploadedOn(LocalDateTime.now());

        while (!storageSizeInMBFuture.isDone()) {
            log.debug("Calculating size of storage...");
            Thread.sleep(100);
        }

        if (storageSizeInMBFuture.isDone()) {
            double storageSizeInMB = storageSizeInMBFuture.get();
            if ((storageSizeInMB + file.getFileSizeInMB()) >= getMaxStorageSizeInMB()) {
                throw new StorageSizeMaxedException(storageSizeInMB);
            }
        }

        return file;
    }

    private Double getMultipartFileSizeInMB(MultipartFile multipartFile) {
        return ((double) multipartFile.getSize() / 1024) / 1024;
    }

    private void copyFileToLocation(MultipartFile multipartFile, String filePath) throws IOException {
        File target = new File(filePath);
        if (!target.getParentFile().exists()) {
            if (!target.getParentFile().mkdirs()) {
                throw new IOException("Unable to copy. Failed to create target file directory. " + target.getParentFile());
            }
        }
        if (target.exists()) {
            throw new FileAlreadyExistsException(environment.getProperty("file.upload.fail.exists") + "\n" + filePath);
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
