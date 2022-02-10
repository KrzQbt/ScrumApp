package com.kkubit.eng.scrum.manager.project.service.impl;

import com.kkubit.eng.scrum.manager.authentication.database.UserRepository;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.authentication.security.services.UserDetailsImpl;
import com.kkubit.eng.scrum.manager.project.api.dto.DetailedProjectParticipant;
import com.kkubit.eng.scrum.manager.project.api.dto.ProjectParticipant;
import com.kkubit.eng.scrum.manager.project.database.*;
import com.kkubit.eng.scrum.manager.project.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// participationService to be injected in preauthorize
@Service(value="participationService")
@AllArgsConstructor
public class ProjectParticipationService {
    private final ProjectRepository projectRepository;
    private final ProjectParticipantRepository projectParticipantRepository;
    private final UserRepository userRepository;
    private final UserStoryRepository storyRepository;
    private final BacklogRepository backlogRepository;
    private final BacklogItemRepository backlogItemRepository;

    /*
    * Methods for pre-authorization, which determine if user is entitled to access api
    * */

    public boolean isUserProjectParticipant(UserDetailsImpl userDetails,Object o){
        if (projectRepository.existsById(Long.parseLong(o.toString()))
            && userRepository.existsById(userDetails.getId())
        ) {

            // if there is participation in given project for given user return true else false
            return projectParticipantRepository.findByProjectAndUser(
                    projectRepository.findById(Long.parseLong(o.toString())).get(),
                    userRepository.findById(userDetails.getId()).get()
            ).isPresent();

        }

        return false;


    }
    public boolean isUserProjectEditor(UserDetailsImpl userDetails,Object o){

        if (projectRepository.existsById(Long.parseLong(o.toString()))
                && userRepository.existsById(userDetails.getId())
        ) {

            System.out.println(userDetails.getId() + userDetails.getUsername());
            System.out.println(Long.parseLong(o.toString()));


             if (projectParticipantRepository.findByProjectAndUser(
                     projectRepository.findById(Long.parseLong(o.toString())).get(),
                     userRepository.findById(userDetails.getId()).get()
             ).isPresent()){

                 ProjectParticipantEntity participant =
                         projectParticipantRepository.findByProjectAndUser(
                                 projectRepository.findById(Long.parseLong(o.toString())).get(),
                                 userRepository.findById(userDetails.getId()).get()
                         ).get();


                 System.out.println();
                 for (EProjectEditRole role : EProjectEditRole.values()){
                     if (participant.getRole().toString().equals(role.toString())) {
                         System.out.println("editor");
                         return true;

                     }
                 }


             }

        }

        return false;
    }



    public boolean isUserBacklogViewer(UserDetailsImpl userDetails,Object o){

        if (backlogRepository.findById(Long.parseLong(o.toString())).isEmpty())
            return false;

        BacklogEntity backlog =
                backlogRepository.findById(Long.parseLong(o.toString())).get();
        return isUserProjectParticipant(userDetails,backlog.getProject().getId());




    }

    public boolean isUserBacklogEditor(UserDetailsImpl userDetails,Object o){
        if (backlogRepository.findById(Long.parseLong(o.toString())).isEmpty())
            return false;
        System.out.println("Editor");
        BacklogEntity backlog =
                backlogRepository.findById(Long.parseLong(o.toString())).get();
        return isUserProjectEditor(userDetails,backlog.getProject().getId());

    }



    public boolean isUserAllowedToDeleteBacklogItem(UserDetailsImpl userDetails,Object o){
        if (backlogItemRepository.findById(Long.parseLong(o.toString())).isEmpty())
            return false;

        BacklogItemEntity backlogItem =
                backlogItemRepository.findById(Long.parseLong(o.toString())).get();
        ProjectEntity project = backlogItem.getBacklog().getProject();
        

        return isUserAllowedToDelete(userDetails,project.getId());

    }



    public boolean isUserAllowedToDelete(UserDetailsImpl userDetails,Object projectId){


        if (projectRepository.existsById(Long.parseLong(projectId.toString()))
                && userRepository.existsById(userDetails.getId())
        ) {

            System.out.println(userDetails.getId() + userDetails.getUsername());
            System.out.println(Long.parseLong(projectId.toString()));


            if (projectParticipantRepository.findByProjectAndUser(
                    projectRepository.findById(Long.parseLong(projectId.toString())).get(),
                    userRepository.findById(userDetails.getId()).get()
            ).isPresent()){

                ProjectParticipantEntity participant =
                        projectParticipantRepository.findByProjectAndUser(
                                projectRepository.findById(Long.parseLong(projectId.toString())).get(),
                                userRepository.findById(userDetails.getId()).get()
                        ).get();

                System.out.println();
                for (EProjectDeleteRole role : EProjectDeleteRole.values()){
                    if (participant.getRole().toString().equals(role.toString())) {
                        System.out.println("can delete");
                        return true;

                    }
                }
            }
        }
        return false;
    }


    /* *
    *
    * story delete route does not point project
    * so we extract project from story Table
    * and then check delete permissions from previous method
    *
    * */
    public boolean isUserAllowedToDeleteStory(UserDetailsImpl userDetails,Object storyId){

        if (storyRepository.findById(Long.parseLong(storyId.toString())).isEmpty())
            return false;

        UserStoryEntity story =
                storyRepository.findById(Long.parseLong(storyId.toString())).get();

        return isUserAllowedToDelete(userDetails,story.getProject().getId());
    }

    public boolean isUserParticipationEditor(UserDetailsImpl userDetails,Object participationId){

        if (projectParticipantRepository.findById(Integer.parseInt(participationId.toString())).isEmpty())
            return false;

        ProjectParticipantEntity participant =
                projectParticipantRepository.findById(Integer.parseInt(participationId.toString())).get();

        return isUserAllowedToDelete(userDetails,participant.getProject().getId());


    }


    /*
    * methods to get all participants
    * */
    public LinkedList<DetailedProjectParticipant> getAllProjectParticipants(Long projectId){
        if(projectRepository.findById(projectId).isEmpty())
            return null;

        ProjectEntity projectEntity = projectRepository.findById(projectId).get();

        if (projectParticipantRepository.findAllByProject(projectEntity).isEmpty())
            return null;

        return projectParticipantRepository.findAllByProject(projectEntity)
                .stream()
                .map(ProjectParticipationService::mapEntityToDetailedParticipant)
                .collect(Collectors.toCollection(LinkedList::new));

    }


    public LinkedList<String> getPossibleProjectRoles(){
        return  Stream.of(EProjectRole.values())
                .map(EProjectRole::name)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /*
    * method to set new role
    * */

    public void setNewProjectRole(ProjectParticipant projectParticipant){
        // check if user and project exist
        if (projectRepository.findById(
                projectParticipant.getProjectId()).isEmpty()
                &&
                userRepository.findById(
                projectParticipant.getUserId()).isEmpty()
        )
            return;

        ProjectEntity projectEntity = projectRepository.findById(
                projectParticipant.getProjectId()
        ).get();
        UserEntity userEntity = userRepository.findById(
                projectParticipant.getUserId()
        ).get();

        // if user was not participant, they will be added with role
        if (projectParticipantRepository.findByProjectAndUser(projectEntity,userEntity).isEmpty()){
            projectParticipantRepository.save(
                    ProjectParticipantEntity.builder()
                            .project(projectEntity)
                            .user(userEntity)
                            .role(EProjectRole.valueOf(projectParticipant.getRole()))
                            .build()
            );
            return;
        }


    // there is participation so it is retrieved, role is changed and saved again
        ProjectParticipantEntity participantEntity = projectParticipantRepository.findByProjectAndUser(projectEntity,userEntity).get();

        System.out.println(projectParticipant.getRole());
        participantEntity.setRole(EProjectRole.valueOf(projectParticipant.getRole()));

        projectParticipantRepository.save(participantEntity);


    }


    public void removeParticipantFromProject(Integer participationId){
        projectParticipantRepository.deleteById(participationId);
    }




    private static DetailedProjectParticipant mapEntityToDetailedParticipant(ProjectParticipantEntity projectParticipantEntity){
        return DetailedProjectParticipant.builder()
                .id(projectParticipantEntity.getId())
                .projectId(projectParticipantEntity.getProject().getId())
                .userId(projectParticipantEntity.getUser().getId())
                .username(projectParticipantEntity.getUser().getUsername())
                .role(projectParticipantEntity.getRole().name())
                .build();
    }
}
