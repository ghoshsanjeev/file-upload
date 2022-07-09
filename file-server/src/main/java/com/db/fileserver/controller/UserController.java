package com.db.fileserver.controller;

import com.db.fileserver.dto.UserDTO;
import com.db.fileserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sanjeev on 10-07-2022
 * @Project: file-server
 */

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private Environment environment;

    @PostMapping(value ="/users", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.ok(environment.getProperty("user.create.success"));
    }
}
