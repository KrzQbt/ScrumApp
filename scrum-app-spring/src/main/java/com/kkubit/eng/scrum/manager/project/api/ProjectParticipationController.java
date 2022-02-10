package com.kkubit.eng.scrum.manager.project.api;

import com.kkubit.eng.scrum.manager.project.api.dto.DetailedProjectParticipant;
import com.kkubit.eng.scrum.manager.project.api.dto.ProjectParticipant;
import com.kkubit.eng.scrum.manager.project.service.impl.ProjectParticipationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class ProjectParticipationController {

    private final ProjectParticipationService participationService;



    @GetMapping("/project/{pid}/participation")
    @PreAuthorize("@participationService.isUserProjectParticipant(principal,@prePathCatcher.getPathVariableByName('pid'))")
    LinkedList<DetailedProjectParticipant> getProjectParticipants(@PathVariable Long pid){

        return participationService.getAllProjectParticipants(pid);
    }

    @PostMapping("/project/{pid}/participation")
    @PreAuthorize("@participationService.isUserProjectEditor(principal,@prePathCatcher.getPathVariableByName('pid'))")
    void addUserToProject(@PathVariable Long pid, @RequestBody ProjectParticipant projectParticipant){
//        System.out.println("part"+projectParticipant);
    participationService.setNewProjectRole(projectParticipant);

    }

    @PatchMapping("/participation/{pid}")
    @PreAuthorize("@participationService.isUserParticipationEditor(principal,@prePathCatcher.getPathVariableByName('pid'))")
    void changeUserRole(@PathVariable Long pid, @RequestBody ProjectParticipant projectParticipant){
        //remove participation
    }

    @DeleteMapping("/participation/{pid}")
    @PreAuthorize("@participationService.isUserParticipationEditor(principal,@prePathCatcher.getPathVariableByName('pid'))")
    void removeUserFromProject(@PathVariable Integer pid){
        //remove participation
        participationService.removeParticipantFromProject(pid
        );
    }

    @GetMapping("/project/roles")
    @PreAuthorize("hasRole('USER')")
    LinkedList<String> getPossibleProjectRoles(){
        System.out.println("roles");
        return participationService.getPossibleProjectRoles();
    }



}
