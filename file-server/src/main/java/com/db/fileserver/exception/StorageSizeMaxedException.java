package com.db.fileserver.exception;

/**
 * @author Sanjeev on 10-07-2022
 * @Project: file-server
 */

public class StorageSizeMaxedException extends RuntimeException {
    public StorageSizeMaxedException(double size) {
        super(size + " MB");
    }
}
