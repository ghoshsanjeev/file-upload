package com.db.fileserver.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

}
