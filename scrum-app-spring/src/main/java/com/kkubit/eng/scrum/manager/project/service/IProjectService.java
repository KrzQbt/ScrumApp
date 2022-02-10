package com.kkubit.eng.scrum.manager.project.service;

import com.kkubit.eng.scrum.manager.project.api.dto.Backlog;
import com.kkubit.eng.scrum.manager.project.api.dto.NewProject;
import com.kkubit.eng.scrum.manager.project.api.dto.Project;
import com.kkubit.eng.scrum.manager.project.api.dto.UserStory;
import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;

import java.security.Principal;
import java.util.LinkedList;

public interface IProjectService {
    void createNewProject(NewProject project, Principal principal);
    LinkedList<Project> getAllProjects();
    Project getProject(Long projectId);
    LinkedList<Backlog> getBacklogList(Long pid);
    void initiateProjectAndBacklog();
    LinkedList<UserStory> getProjectStories(Long projectId);
    void createUserStory(Long projectId,UserStory userStory);
    void deleteStory(Long storyId);
    void updateProject(Long projectId, Project project);
    void deleteProject(Long projectId);
    LinkedList<Project> getMyProjects(Principal principal);




    }
