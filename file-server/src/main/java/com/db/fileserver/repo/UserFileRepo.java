package com.db.fileserver.repo;

import com.db.fileserver.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

public interface UserFileRepo extends JpaRepository<UserFile, Long> {

}
