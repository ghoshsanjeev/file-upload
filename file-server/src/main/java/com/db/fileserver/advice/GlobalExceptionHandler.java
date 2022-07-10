package com.db.fileserver.advice;

import com.db.fileserver.exception.StorageSizeMaxedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private Environment environment;

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserLoginException(UsernameNotFoundException ex, HttpServletRequest req) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), EXPECTATION_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest req, Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = StorageSizeMaxedException.class)
    public ResponseEntity<?> handleStorageSizeMaxedException(StorageSizeMaxedException ex, HttpServletRequest req) {
        String message = environment.getProperty("file.upload.fail.storage-full") + ": " + ex.getMessage();
        log.error(message);
        return new ResponseEntity<>(message, INTERNAL_SERVER_ERROR);
    }
}
