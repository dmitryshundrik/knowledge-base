package com.dmitryshundrik.knowledgebase.security.model;

import lombok.Data;

@Data
public class UserSignupDTO {

    private String name;

    private String username;

    private String password;
}
