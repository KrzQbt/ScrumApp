package com.kkubit.eng.scrum.manager.authentication.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Set<String> role;
}
