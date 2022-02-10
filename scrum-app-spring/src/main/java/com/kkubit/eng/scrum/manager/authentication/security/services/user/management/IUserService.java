package com.kkubit.eng.scrum.manager.authentication.security.services.user.management;

import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.User;
import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.UserPatchObject;

import java.util.LinkedList;

public interface IUserService {
    User getUserDetails(Long id);
    void userDetailsChange(Long id, UserPatchObject patchObject);
    LinkedList <User> getAllUsersList();
}
