package com.kkubit.eng.scrum.manager.project.service;

import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface IProjectParticipationService {
    boolean isUserParticipant(Authentication auth);
    boolean isUserParticipant();
    boolean isUserRoleSufficient();
    boolean isProjectRoleSufficient(Principal principal);
}
