package com.db.fileserver.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author Sanjeev on 09-07-2022
 * @Project: file-server
 */

@Entity
@Table(name = "User")
@Setter
@Getter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(unique=true)
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "uploader")
    private List<UserFile> userFiles;
}
