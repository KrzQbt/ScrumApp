package com.kkubit.eng.scrum.manager.project.service.impl;

import com.kkubit.eng.scrum.manager.authentication.database.UserRepository;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.project.api.dto.*;
import com.kkubit.eng.scrum.manager.project.database.*;
import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;
import com.kkubit.eng.scrum.manager.project.model.BacklogItemEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import com.kkubit.eng.scrum.manager.project.model.UserStoryEntity;
import com.kkubit.eng.scrum.manager.project.service.IProjectParticipationService;
import com.kkubit.eng.scrum.manager.project.service.IProjectService;
import com.kkubit.eng.scrum.manager.sprint.api.dto.Sprint;
import com.kkubit.eng.scrum.manager.sprint.database.SprintRepository;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService implements IProjectService {
    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final BacklogItemRepository backlogItemRepository;
    private final UserRepository userRepository;
    private final UserStoryRepository storyRepository;
    private final ProjectParticipantRepository participantRepository;

    private final ProjectParticipationService participationService;
    private final ISprintService sprintService;

    @Override
    public void createNewProject(NewProject project, Principal principal) {

        if (userRepository.findByUsername(principal.getName()).isEmpty())
            return;
        UserEntity userEntity =
                userRepository.findByUsername(principal.getName()).get();

        ProjectEntity projectEntity = projectRepository.save(ProjectEntity.builder()
                .name(project.getProjectName())
                .build()
        );


        participationService.setNewProjectRole(
                ProjectParticipant.builder()
                        .projectId(projectEntity.getId())
                        .userId(userEntity.getId())
                        .role(project.getCreatorRole())
                        .build()
        );


    }

    @Override
    public LinkedList<Project> getAllProjects(){
        return projectRepository.findAll()
                .stream()
                .map(ProjectService::mapEntityToProject)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    public Project getProject(Long projectId){
        if (projectRepository.findById(projectId).isEmpty())
            return null;
        else
            return mapEntityToProject(projectRepository.findById(projectId).get());
    }

    @Override
    public LinkedList<Backlog> getBacklogList(Long pid){

        if (projectRepository.findById(pid).isEmpty())
            return null;
        else
            return backlogRepository.findAllByProject(
                    projectRepository.findById(pid).get())
                    .stream()
                    .map(ProjectService::mapEntityToBacklog)
                    .collect(Collectors.toCollection(LinkedList::new));


    }

    @Override
    public LinkedList<UserStory> getProjectStories(Long projectId) {
        if (projectRepository.findById(projectId).isEmpty())
            return null;
        ProjectEntity projectEntity = projectRepository.findById(projectId).get();

        if (storyRepository.findAllByProject(projectEntity).isEmpty())
            return null;


        return storyRepository.findAllByProject(projectEntity).stream()
                .map(ProjectService::mapEntityToStory)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void createUserStory(Long projectId, UserStory userStory) {
        if (projectRepository.findById(projectId).isEmpty())
            return;
        ProjectEntity projectEntity =
                projectRepository.findById(projectId).get();


        storyRepository.save(
                UserStoryEntity.builder()
                        .story(userStory.getStory())
                        .project(projectEntity)
                        .build()
        );


    }

    @Override
    public void deleteStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }


    @Override
    public void updateProject(Long projectId, Project project) {
        if (projectRepository.findById(projectId).isEmpty())
            return;

        ProjectEntity projectEntity =
                projectRepository.findById(projectId).get();

        if (project.getName()==null)
                return;

        projectEntity.setName(project.getName());

        projectRepository.save(projectEntity);

    }

    @Override
    public void deleteProject(Long projectId) {
        // del all in project
        // del all sprints backlogs and participation, then del project
        if (projectRepository.findById(projectId).isEmpty())
            return;

        ProjectEntity project =
                projectRepository.findById(projectId).get();


                sprintService.getAllSprintsInProject(projectId)
                        .forEach( sprint -> {
            sprintService.deleteSprint(sprint.getId());
        });

        LinkedList<BacklogEntity> backlogs =
                backlogRepository.findAllByProject(project);
        backlogs.forEach(backlogItemRepository::deleteByBacklog);
        backlogs.forEach(backlogRepository::delete);


        participationService.getAllProjectParticipants(projectId)
                .forEach( participant -> {participationService.removeParticipantFromProject(participant.getId());});

        storyRepository.findAllByProject(project).forEach(storyRepository::delete);


        projectRepository.deleteById(projectId);
    }

    @Override
    public LinkedList<Project> getMyProjects(Principal principal) {
        if (userRepository.findByUsername(principal.getName()).isEmpty())
            return new LinkedList<>();

        UserEntity user = userRepository.findByUsername(principal.getName()).get();
        LinkedList<Project> projects = new LinkedList<>();

        participantRepository.findAllByUser(user)
                .forEach(
                        participant -> {
                            if (projectRepository.findById(participant.getProject().getId()).isPresent())
                                projects.add(mapEntityToProject(
                                        projectRepository.findById(
                                                participant.getProject().getId()
                                        ).get()
                                ));
                        }
                );


        return projects;
    }

    /*
    * helper methods for mapping results from entity to object
    */
    private static Backlog mapEntityToBacklog(BacklogEntity backlogEntity){
        return Backlog.builder()
                .id(backlogEntity.getId())
                .description(backlogEntity.getDescription())
                .projectId(backlogEntity.getProject().getId())
                .current(backlogEntity.getCurrent())
                .build();
    }

    private static Project mapEntityToProject(ProjectEntity projectEntity){
        return Project.builder()
                .id(projectEntity.getId())
                .name(projectEntity.getName())
                .build();
    }

    private static UserStory mapEntityToStory(UserStoryEntity storyEntity){
        return UserStory.builder()
                .id(storyEntity.getId())
                .story(storyEntity.getStory())
                .projectId(storyEntity.getProject().getId())
                .build();
    }

    /*
    *Method to populate DB
    * */
    @Override
    public void initiateProjectAndBacklog(){

        for (long i=1; i<=3; i++) {
            projectRepository.save(ProjectEntity.builder()

                    .name("project "+i)
                    .build()
            );
            for (long j=1; j<=2; j++){
                backlogRepository.save(BacklogEntity.builder()

                        .description("backlog " + j +" description")
                        .project(projectRepository.findById(i).get())
                        .build()
                );

            }

        }

        for (long k=1; k<=6; k++){

            for (int i = 0; i < 4; i++) {


                backlogItemRepository.save(BacklogItemEntity.builder()

                        .description("corgo finder " + i + " desc")
                        .details("some important details " + i)
                        .name("corgo name " + i)
                        .priority((int) i)
                        .releaseV("2." +i)
                        .type("feature")
                        .story("i want a corgo "+ i)
                        .backlog(backlogRepository.findById(k).get())
                        .build()
                );
            }

        }


    }
}