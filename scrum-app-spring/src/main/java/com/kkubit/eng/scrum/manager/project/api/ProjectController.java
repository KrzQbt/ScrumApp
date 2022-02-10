package com.kkubit.eng.scrum.manager.project.api;


import com.kkubit.eng.scrum.manager.project.api.dto.Backlog;
import com.kkubit.eng.scrum.manager.project.api.dto.NewProject;
import com.kkubit.eng.scrum.manager.project.api.dto.Project;
import com.kkubit.eng.scrum.manager.project.api.dto.UserStory;
import com.kkubit.eng.scrum.manager.project.service.IProjectParticipationService;
import com.kkubit.eng.scrum.manager.project.service.IProjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class ProjectController {
    private final IProjectService projectService;


    @GetMapping("/project/all")
    @PreAuthorize("hasRole('USER') ")
    LinkedList<Project> getProjects(){

        return projectService.getAllProjects();
    }

    @GetMapping("/project/{pid}")
    @PreAuthorize("@participationService.isUserProjectParticipant(principal,@prePathCatcher.getPathVariableByName('pid'))")
    Project getProjectInfo(@PathVariable Long pid){
        return projectService.getProject(pid);
    }

    @PutMapping("/project/{pid}")
    @PreAuthorize("@participationService.isUserParticipant(principal)")
    void updateProject(@PathVariable Long pid, @RequestBody Project project){
        projectService.updateProject(pid,project);
    }

    @DeleteMapping("/project/{pid}")
    @PreAuthorize("@participationService.isUserAllowedToDelete(principal,@prePathCatcher.getPathVariableByName('pid'))")
    void deleteProject(@PathVariable Long pid){
        projectService.deleteProject(pid);
    }


    @GetMapping("/project/{pid}/backlog")
    @PreAuthorize("@participationService.isUserProjectParticipant(principal,@prePathCatcher.getPathVariableByName('pid'))")
//    @PreAuthorize("@participationService.isUserProjectEditor(principal,@prePathCatcher.getPathVariableByName('pid'))")
    LinkedList<Backlog> getProjectBacklogList(@PathVariable Long pid){
        return projectService.getBacklogList(pid);

    }

    @PostMapping("/project")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    void createNewProject(@RequestBody NewProject newProject, Principal principal){
        projectService.createNewProject(newProject,principal);
//        System.out.println(newProject.toString() + principal.getName());


    }

    @GetMapping("/project/{pid}/story")
    @PreAuthorize("@participationService.isUserProjectParticipant(principal,@prePathCatcher.getPathVariableByName('pid'))")
    LinkedList<UserStory> getStoriesInProject(@PathVariable Long pid){

        return projectService.getProjectStories(pid);
    }


    @PostMapping("/project/{pid}/story")
    @PreAuthorize("@participationService.isUserProjectEditor(principal,@prePathCatcher.getPathVariableByName('pid'))")
    void createNewStory(@PathVariable Long pid, @RequestBody UserStory userStory){

        System.out.println(userStory);
        projectService.createUserStory(pid,userStory);
    }

    @DeleteMapping("/story/{sid}")
    @PreAuthorize("@participationService.isUserAllowedToDeleteStory(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void deleteStory(@PathVariable Long sid){
        projectService.deleteStory(sid);
    }

    // any role and no need to preauth because result will be determined only on token
    @GetMapping("/project/")
    @PreAuthorize("hasRole('USER') ")
    LinkedList<Project> getMyProjects(Principal principal){

        return projectService.getMyProjects(principal);
    }

//    private final IProjectParticipationService participationService;

//    @GetMapping("/start")
//    void start(){
//        projectService.initiateProjectAndBacklog();
//    }


}
