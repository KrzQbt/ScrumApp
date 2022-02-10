package com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper;

import com.kkubit.eng.scrum.manager.authentication.model.SystemRoleEntity;
import lombok.Getter;


import java.util.LinkedList;

@Getter

public class UserPatchObject {
    // you can change pass, name, roles, cant change id username etc
    private String password;
    private String name;
    private String surname;
    private LinkedList<String> roles;
}
