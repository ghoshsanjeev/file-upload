package com.db.fileserver.repo;

import com.db.fileserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
