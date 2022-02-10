package com.kkubit.eng.scrum.manager.authentication.api;


import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.User;
import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.UserPatchObject;
import com.kkubit.eng.scrum.manager.authentication.security.services.user.management.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/user/{uid}")
    @PreAuthorize("hasRole('USER')")
    public User getUserDetails(@PathVariable Long uid){

        System.out.println(userService.getUserDetails(uid).toString());
        return userService.getUserDetails(uid);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public LinkedList<User> getAllUsers(){

        return userService.getAllUsersList();
    }



}
