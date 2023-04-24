package org.example.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;

    private String username;
    private String fullname;

    private String password;
    private String oldPassword;

    private Date registrationDate = new Date();

    private Set<String> roles;

    private boolean nonLocked = true;

    private String mail;

    private boolean active = false;

    private String activationCode;
}
