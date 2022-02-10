package com.kkubit.eng.scrum.manager.authentication.security.services.user.management.impl;

import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.User;
import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.UserPatchObject;
import com.kkubit.eng.scrum.manager.authentication.database.SystemRoleRepository;
import com.kkubit.eng.scrum.manager.authentication.database.UserRepository;
import com.kkubit.eng.scrum.manager.authentication.model.ESystemRole;
import com.kkubit.eng.scrum.manager.authentication.model.SystemRoleEntity;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.authentication.security.services.user.management.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final SystemRoleRepository roleRepository;


    @Override
    public User getUserDetails(Long id) {

        if (userRepository.findById(id).isEmpty())
            return null;
        return mapEntityToUser(userRepository.findById(id).get());
    }

    @Override
    public void userDetailsChange(Long id, UserPatchObject patchObject) {

        if (userRepository.findById(id).isPresent()) {
            UserEntity user = userRepository.findById(id).get();
            if( patchObject.getName() != null )
                user.setName(patchObject.getName());
            if( patchObject.getSurname() != null )
                user.setName(patchObject.getSurname());
            if( patchObject.getPassword() != null )
                user.setPassword(
                        new BCryptPasswordEncoder().encode(patchObject.getPassword())
                );
            if( patchObject.getRoles() != null ){

                Set<SystemRoleEntity> roles = new HashSet<>();
                patchObject.getRoles().forEach(
                        role ->{
                            //check if role is proper
                            if (roleRepository.findByName(ESystemRole.valueOf(role)).isPresent())
                                roles.add(
                                        roleRepository.findByName(ESystemRole.valueOf(role)).get()
                                );
                        }
                );
                user.setRoles(roles);
            }
            userRepository.save(user);
        }

    }



    @Override
    public LinkedList<User> getAllUsersList() {
        return userRepository.findAll()
                .stream()
                .map(UserService::mapEntityToUser)
                .collect(Collectors.toCollection(LinkedList::new));
    }



    private static User mapEntityToUser(UserEntity userEntity){
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .roles(
                        userEntity.getRoles()
                                .stream()
                                .map(systemRoleEntity -> { return systemRoleEntity.getName().toString(); })
                                .collect(Collectors.toCollection(LinkedList::new))

                )
                .build();
    }


}

