package com.kkubit.eng.scrum.manager.sprint.service;

import com.kkubit.eng.scrum.manager.authentication.database.UserRepository;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.project.database.BacklogItemRepository;
import com.kkubit.eng.scrum.manager.project.database.BacklogRepository;
import com.kkubit.eng.scrum.manager.project.database.ProjectRepository;
import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;
import com.kkubit.eng.scrum.manager.sprint.database.*;
import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintParticipantEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintPickedItemEntity;
import com.kkubit.eng.scrum.manager.sprint.model.TaskEntity;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ESprintRole;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ETaskStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.LinkedList;

@Service
@AllArgsConstructor
public class StartService {
    ProjectRepository projectRepository;
    BacklogRepository backlogRepository;
    BacklogItemRepository backlogItemRepository;
    UserRepository userRepository;

    DailySprintTimePreferenceRepository dailyRepository;
    SprintParticipantRepository participantRepository;
    SprintPickedItemRepository pickedItemRepository;
    SprintRepository sprintRepository;
    SprintTimePreferenceRepository timePreferenceRepository;
    TaskRepository taskRepository;


    public void startSprint(){

        sprintRepository.save(
                SprintEntity.builder()
                        .description("Sprint uno with goals")
                        .current(false)
                        .inPlaning(false)
                        .finished(true)
                        .startTime(Date.valueOf("2021-12-20"))
                        .endTime(Date.valueOf("2021-12-30"))
                        .backlog(backlogRepository.findById((long)2).get())
                        .project(projectRepository.findById((long) 1).get())
                        .build()
        );// sprint added

        sprintRepository.save(
                SprintEntity.builder()
                        .description("Sprint duo with goals")
                        .inPlaning(false)
                        .current(true)
                        .finished(false)
                        .startTime(Date.valueOf("2022-01-04"))
                        .endTime(Date.valueOf("2022-01-07"))
                        .backlog(backlogRepository.findById((long)2).get())
                        .project(projectRepository.findById((long) 1).get())
                        .build()
        );// sprint added

        LinkedList<UserEntity> usersToParticipate = new LinkedList<>();
        usersToParticipate.add(userRepository.findByUsername("a11").get());
        usersToParticipate.add(userRepository.findByUsername("a12").get());
        usersToParticipate.add(userRepository.findByUsername("a13").get());

        // adding participants to first, finished sprint
        participantRepository.save(
                SprintParticipantEntity.builder()
                        .role(ESprintRole.ROLE_PROGRAMMER)
                        .user(usersToParticipate.get(0))
                        .sprint(sprintRepository.findById((long) 1).get())
                        .build()
        );
        participantRepository.save(
                SprintParticipantEntity.builder()
                        .role(ESprintRole.ROLE_PRODUCT_OWNER)
                        .user(usersToParticipate.get(1))
                        .sprint(sprintRepository.findById((long) 1).get())
                        .build()
        );
        participantRepository.save(
                SprintParticipantEntity.builder()
                        .role(ESprintRole.ROLE_TEAM_LEADER)
                        .user(usersToParticipate.get(2))
                        .sprint(sprintRepository.findById((long) 1).get())
                        .build()
        );

        // adding participants to 2nd sprint
        participantRepository.save(
                SprintParticipantEntity.builder()
                        .role(ESprintRole.ROLE_PROGRAMMER)
                        .user(usersToParticipate.get(0))
                        .sprint(sprintRepository.findById((long) 2).get())
                        .build()
        );
        participantRepository.save(
                SprintParticipantEntity.builder()
                        .role(ESprintRole.ROLE_PRODUCT_OWNER)
                        .user(usersToParticipate.get(1))
                        .sprint(sprintRepository.findById((long) 2).get())
                        .build()
        );
        participantRepository.save(
                SprintParticipantEntity.builder()
                        .role(ESprintRole.ROLE_TEAM_LEADER)
                        .user(usersToParticipate.get(2))
                        .sprint(sprintRepository.findById((long) 2).get())
                        .build()
        );

    // adding items to sprint 1
    pickedItemRepository.save(
            SprintPickedItemEntity.builder()
                .backlogItem(backlogItemRepository.findById((long) 5).get())
                .sprintEntity(sprintRepository.findById((long) 1).get())
                .build()
    );
    pickedItemRepository.save(
            SprintPickedItemEntity.builder()
                .backlogItem(backlogItemRepository.findById((long) 6).get())
                .sprintEntity(sprintRepository.findById((long) 1).get())
                .build()
    );
    pickedItemRepository.save(
            SprintPickedItemEntity.builder()
                        .backlogItem(backlogItemRepository.findById((long) 7).get())
                        .sprintEntity(sprintRepository.findById((long) 2).get())
                        .build()
        );
        // adding items to sprint 2
    pickedItemRepository.save(
            SprintPickedItemEntity.builder()
                        .backlogItem(backlogItemRepository.findById((long) 8).get())
                        .sprintEntity(sprintRepository.findById((long) 2).get())
                        .build()
        );

    // adding tasks to sprint1
    taskRepository.save(
            TaskEntity.builder()
                    .name("do things 1")
                    .description("Task 1 some things to do")
                    .sprint(sprintRepository.findById((long) 1).get())
                    .pickedItem(pickedItemRepository.findById((long) 1).get())
                    .status(ETaskStatus.FREE)

                    .build()
    );

    taskRepository.save(
                TaskEntity.builder()
                        .name("do things 2")
                        .description("Task 1 some things to do")
                        .sprint(sprintRepository.findById((long) 1).get())
                        .pickedItem(pickedItemRepository.findById((long) 2).get())
                        .status(ETaskStatus.FREE)

                        .build()
        );
        // adding tasks to sprint2
    taskRepository.save(
                TaskEntity.builder()
                        .name("do things 2")
                        .description("Task 1 some things to do")
                        .sprint(sprintRepository.findById((long) 2).get())
                        .pickedItem(pickedItemRepository.findById((long) 3).get())
                        .status(ETaskStatus.FREE)

                        .build()
        );
        taskRepository.save(
                TaskEntity.builder()
                        .name("do things 2")
                        .description("Task 1 some things to do")
                        .sprint(sprintRepository.findById((long) 2).get())
                        .pickedItem(pickedItemRepository.findById((long) 4).get())
                        .status(ETaskStatus.FREE)
                        .build()
        );







    }


}
