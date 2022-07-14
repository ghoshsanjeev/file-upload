package com.db.fileserver.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

@Entity
@Table(name = "UserFile")
@Setter
@Getter
@EqualsAndHashCode
public class UserFile {

    public UserFile(String path) {
        setFilePath(path);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fileId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User uploader;

    @Column
    private String fileName;

    @Column
    private String filePath;

    @Column
    private String fileExtension;

    @Column(name = "file_size_in_mb")
    private Double fileSizeInMB;

    @Column
    private String comment;

    @Column
    private LocalDateTime uploadedOn;

    public String getFilePath() {
        return filePath;
    }

    public Double getFileSizeInMB() {
        return fileSizeInMB;
    }

    public void setFilePath(String filePath) {
        File file = new File(filePath);
        this.filePath = file.getAbsolutePath();
    }

    public void setFileSizeInMB(Double fileSizeInMB) {
        this.fileSizeInMB = BigDecimal.valueOf(fileSizeInMB)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
