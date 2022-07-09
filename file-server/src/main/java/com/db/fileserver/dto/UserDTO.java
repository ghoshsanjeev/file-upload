package com.db.fileserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sanjeev on 10-07-2022
 * @Project: file-server
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public class UserDTO {
    private String username;

    private String firstName;

    private String lastName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String password;
}
