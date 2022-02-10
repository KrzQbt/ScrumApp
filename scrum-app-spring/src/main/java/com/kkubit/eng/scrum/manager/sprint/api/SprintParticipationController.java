package com.kkubit.eng.scrum.manager.sprint.api;

import com.kkubit.eng.scrum.manager.sprint.api.dto.DetailedSprintParticipant;
import com.kkubit.eng.scrum.manager.sprint.api.dto.SprintParticipant;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintParticipationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class SprintParticipationController {

    private final ISprintParticipationService participationService;

    @GetMapping("/sprint/{sid}/participation")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    List<DetailedSprintParticipant> getSprintParticipants(@PathVariable Long sid){
        participationService.getAllSprintParticipants(sid).forEach(System.out::println);
        return participationService.getAllSprintParticipants(sid);
    }


    @PostMapping("/sprint/{sid}/participation")
    @PreAuthorize("@sprintParticipationService.isUserSprintEditor(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void addUserToSprint(@PathVariable Long sid, @RequestBody SprintParticipant sprintParticipant){

        participationService.setNewSprintRole(sprintParticipant);
    }

    @DeleteMapping("sprint/participation/{pid}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    void removeUserFromSprint(@PathVariable Long pid){

        participationService.removeParticipantFromSprint(pid);
    }

    @GetMapping("/sprint/roles")
    @PreAuthorize("hasRole('USER')")
    List<String> getPossibleProjectRoles(){
        System.out.println("roles");
        return participationService.getPossibleProjectRoles();
    }



}
