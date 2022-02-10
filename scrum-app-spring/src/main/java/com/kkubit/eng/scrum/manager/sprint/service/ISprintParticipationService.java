package com.kkubit.eng.scrum.manager.sprint.service;

import com.kkubit.eng.scrum.manager.authentication.security.services.UserDetailsImpl;
import com.kkubit.eng.scrum.manager.sprint.api.dto.DetailedSprintParticipant;
import com.kkubit.eng.scrum.manager.sprint.api.dto.SprintParticipant;

import java.util.LinkedList;
import java.util.List;

public interface ISprintParticipationService {
    boolean isUserSprintParticipant(UserDetailsImpl userDetails,Object sprintId);
    boolean isUserRoleSufficient(UserDetailsImpl userDetails);
    List<DetailedSprintParticipant> getAllSprintParticipants(Long sprintId);
    void setNewSprintRole(SprintParticipant sprintParticipant);
    void removeParticipantFromSprint(Long participationId);

    List<String> getPossibleProjectRoles();

}
