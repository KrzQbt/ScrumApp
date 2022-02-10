package com.kkubit.eng.scrum.manager.sprint.service.impl;

import com.kkubit.eng.scrum.manager.authentication.database.UserRepository;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.project.api.dto.BacklogItem;
import com.kkubit.eng.scrum.manager.project.database.BacklogItemRepository;
import com.kkubit.eng.scrum.manager.project.database.BacklogRepository;
import com.kkubit.eng.scrum.manager.project.database.ProjectRepository;
import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;
import com.kkubit.eng.scrum.manager.project.model.BacklogItemEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import com.kkubit.eng.scrum.manager.project.service.impl.BacklogService;
import com.kkubit.eng.scrum.manager.sprint.api.dto.*;
import com.kkubit.eng.scrum.manager.sprint.database.*;
import com.kkubit.eng.scrum.manager.sprint.model.*;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ETaskStatus;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintParticipationService;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SprintServiceImpl implements ISprintService {

    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final SprintPickedItemRepository pickedItemRepository;
    private final BacklogItemRepository backlogItemRepository;
    private final TaskRepository taskRepository;
    private final BacklogRepository backlogRepository;
    private final UserRepository userRepository;
    private final SprintParticipantRepository participantRepository;
    private final SprintTimePreferenceRepository preferenceRepository;
    private final DailySprintTimePreferenceRepository dailyPreferenceRepository;

    private final ISprintParticipationService participationService;


    @Override
    public LinkedList<Sprint> getMySprints(Principal principal) {
        if (userRepository.findByUsername(principal.getName()).isEmpty())
            return new LinkedList<>();

        UserEntity user =
                userRepository.findByUsername(principal.getName()).get();

        LinkedList<Sprint> sprints = new LinkedList<>();

        participantRepository.findAllByUser(user).forEach(
                participant -> {
                    if (sprintRepository.findById(participant.getSprint().getId()).isPresent())

                        sprints.add(

                                mapEntityToSprint(
                                        sprintRepository.findById(participant.getSprint().getId()).get()
                                )
                        );
                }
        );


        return sprints.stream().filter(sprint -> sprint.getCurrent() ==true).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public LinkedList<Task> getTaskListForSprint(Long sprintId) {
        if (sprintRepository.findById(sprintId).isEmpty())
            return null;

        return taskRepository.findAllBySprint(sprintRepository.findById(sprintId).get())
                .stream()
                .map(SprintServiceImpl::mapEntityToTask)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void updateTask(Task task) {

    }





    @Override
    public void updateTaskStatus(TaskStatusPatch statusPatch) {
        if (taskRepository.findById(statusPatch.getId()).isEmpty())
            return;
        TaskEntity task = taskRepository.findById(statusPatch.getId()).get();
        task.setStatus(ETaskStatus.valueOf(statusPatch.getStatus()));

        //if task status is DONE - add current date - finished at else null the finished at
        if (ETaskStatus.valueOf(statusPatch.getStatus()) == ETaskStatus.DONE){
            task.setFinishedAt(
                    new java.sql.Date(System.currentTimeMillis())
            );
        }else {
            task.setFinishedAt(null);
        }


        taskRepository.save(task);

    }




    @Override
    public void createTask(Task task) {

        taskRepository.save(mapTaskToEntity(task));
    }



    @Override
    public void deleteTask(Long id) {

        taskRepository.deleteById(id);
    }



    public LinkedList<BacklogItem> getPickedItems(Long sprintId){
        if (sprintRepository.findById(sprintId).isEmpty())
            return null;

        LinkedList<SprintPickedItemEntity> pickedList =
                pickedItemRepository.findAllBySprintEntity(sprintRepository.findById(sprintId).get());

        LinkedList<BacklogItemEntity> backlogItems =
                new LinkedList<>();

        pickedList.forEach(picked ->{
            backlogItems.add(picked.getBacklogItem());
        });

        return backlogItems.stream()
                .map(SprintServiceImpl::mapEntityToBacklogItem)
                .collect(Collectors.toCollection(LinkedList::new));

    }



    @Override
    public BacklogItem getBacklogItemByPickedId(Long itemId) {
        if (pickedItemRepository.findById(itemId).isEmpty())
            return null;


        return mapEntityToBacklogItem(
                pickedItemRepository.findById(itemId).get().getBacklogItem()
        );
    }



    @Override
    public LinkedList<PickedItem> getAlreadyPickedItems(Long sprintId) {
            if (sprintRepository.findById(sprintId).isEmpty())
                return null;

        return pickedItemRepository.findAllBySprintEntity(
                sprintRepository.findById(sprintId).get()
        ).stream()
                .map(SprintServiceImpl::mapEntityToPickedItem)
                .collect(Collectors.toCollection(LinkedList::new));
    }



    @Override
    public void pickItemsForSprint(LinkedList<BacklogItem> backlogItems, Long sprintId) {
        if (sprintRepository.findById(sprintId).isEmpty())
            return;

        SprintEntity sprintEntity =
                sprintRepository.findById(sprintId).get();

        pickedItemRepository.deleteBySprintEntity(sprintEntity);


        backlogItems.forEach(
                backlogItem -> {
                    if (backlogItemRepository.findById(backlogItem.getId()).isPresent())
                    pickedItemRepository.save(
                            SprintPickedItemEntity.builder()
                                    .sprintEntity(sprintEntity)
                                    .backlogItem(backlogItemRepository.findById(backlogItem.getId()).get())
                                    .build());
                }
        );
    }



    @Override
    public void updateSprintItems(LinkedList<PickedItem> pickedItems) {
        // TODO
    }



    @Override
    public Sprint getSprint(Long sprintId) {
        if(sprintRepository.findById(sprintId).isEmpty())
            return null;
        else
            return sprintRepository.findById(sprintId)
                    .map(SprintServiceImpl::mapEntityToSprint)
                    .get();
    }

    @Override
    public LinkedList<Sprint> getAllSprintsInProject(Long projectId) {

        if (projectRepository.findById(projectId).isEmpty())
            return null; // zwrocic liste TODO
        else
            return sprintRepository.findAllByProject(
                    projectRepository.findById(projectId).get()
            ).stream().map(SprintServiceImpl::mapEntityToSprint).collect(Collectors.toCollection(LinkedList::new));

    }





    @Override
    public void createSprint(Long projectId, NewSprintWrapper sprint, String username) {

        // create sprint and add participant
        if (projectRepository.findById(projectId).isEmpty())
            return;
        ProjectEntity projectEntity =
                projectRepository.findById(projectId).get();

        if (backlogRepository.findById(sprint.getBacklogId()).isEmpty())
            return;
        BacklogEntity backlogEntity =
                backlogRepository.findById(sprint.getBacklogId()).get();



        SprintEntity sprintEntity =
                sprintRepository.save(
                        SprintEntity.builder()
                                .description(sprint.getDescription())
                                .current(false)
                                .finished(false)
                                .inPlaning(true)
                                .startTime(Date.valueOf(sprint.getStartTime()))
                                .endTime(Date.valueOf(sprint.getEndTime()))
                                .project(projectEntity)
                                .backlog(backlogEntity)
                                .build()
                );

        // set role
        if (userRepository.findByUsername(username).isEmpty())
            return;

        UserEntity userEntity =
                userRepository.findByUsername(username).get();
        System.out.println("user found");

        if (sprint.getSprintRole() != null)
            participationService.setNewSprintRole(
                    SprintParticipant.builder()
                            .sprintId(sprintEntity.getId())
                            .userId(userEntity.getId())
                            .username(username)
                            .role(sprint.getSprintRole())
                            .build()
            );

        if (sprint.getCopyFromSprintWithId()==null)
            return;



        if (sprintRepository.findById(sprint.getCopyFromSprintWithId()).isEmpty())
            return;
        SprintEntity sprintToCopyPart =
                sprintRepository.findById(sprint.getCopyFromSprintWithId()).get();

        // copy participants
        if (participantRepository.findAllBySprint(sprintToCopyPart).isEmpty())
            return;
        LinkedList<SprintParticipantEntity> oldParticipants = participantRepository.findAllBySprint(sprintToCopyPart);


    oldParticipants.forEach(
            participation -> {
                participation.setSprint(sprintEntity);
                participantRepository.save(participation);
            }
    );




    }



    @Override
    public void changeSprintStatus(Long sprintId, SprintStatusPatch statusPatch) {
        if (sprintRepository.findById(sprintId).isEmpty())
            return;
        SprintEntity sprintEntity =
                sprintRepository.findById(sprintId).get();

        if (statusPatch.getCurrent() == true) {
            sprintEntity.setCurrent(statusPatch.getCurrent());
            sprintEntity.setInPlaning(false);
            sprintEntity.setFinished(false);
        }

        if (statusPatch.getInPlaning() == true) {
            sprintEntity.setInPlaning(statusPatch.getInPlaning());
            sprintEntity.setCurrent(false);
            sprintEntity.setFinished(false);
        }

        if (statusPatch.getFinished() == true) {
            sprintEntity.setFinished(statusPatch.getFinished() );

            sprintEntity.setInPlaning(false);
            sprintEntity.setCurrent(false);

        }

        sprintRepository.save(sprintEntity);

    }

    @Override
    public void updateSprint(Long sprintId, Sprint sprint) {

        if (sprintRepository.findById(sprintId).isEmpty())
            return;

        SprintEntity sprintEntity =
                sprintRepository.findById(sprintId).get();

            if (sprint.getDescription()!=null)
                sprintEntity.setDescription(sprint.getDescription());
            if (sprint.getStartTime()!=null)
                sprintEntity.setStartTime(Date.valueOf(sprint.getStartTime()));
            if (sprint.getEndTime()!=null)
                sprintEntity.setEndTime(Date.valueOf(sprint.getEndTime()));



            if (sprint.getBacklogId()!=null &&
                    ! sprint.getBacklogId().equals(sprintEntity.getBacklog().getId())){

                if (backlogRepository.findById(sprintId).isPresent()){


                    if(sprintEntity.getBacklog().getId().equals( sprint.getBacklogId())) {

                        BacklogEntity backlogEntity =
                                backlogRepository.findById(sprint.getBacklogId()).get();

                        // deletion of both tasks and picked items, as they rely on backlog vertion
                        taskRepository.deleteBySprint(sprintEntity);
                        pickedItemRepository.deleteBySprintEntity(sprintEntity);
                        sprintEntity.setBacklog(backlogEntity);
                    }
                }
            }

        sprintRepository.save(sprintEntity);
    }



    @Override
    public void deleteSprint(Long sprintId) {


        if (sprintRepository.findById(sprintId).isEmpty())
            return;
        SprintEntity sprintEntity =
                sprintRepository.findById(sprintId).get();

        // delete time preferences
        LinkedList<SprintTimePreferenceEntity> preferences =
                preferenceRepository.findAllBySprintEntity(sprintEntity);

        preferences.forEach(dailyPreferenceRepository::deleteByPreference);
        preferences.forEach(
                preference -> {
                    preferenceRepository.deleteById(preference.getId());
                }
        );

        // delete tasks
        taskRepository.deleteBySprint(sprintEntity);

        // delete picked items
        pickedItemRepository.deleteBySprintEntity(sprintEntity);

        // delete participants
        participantRepository.deleteBySprint(sprintEntity);

        // delete sprint
        sprintRepository.delete(sprintEntity);

    }



    @Override
    public TimePreferenceWrapper getParticipantTimePreferenceForSprint(Long participantId, Long sprintId) {
        return null; // remove as feature was moved to TimeManagement Interface TODO
    }



    private static PickedItem mapEntityToPickedItem(SprintPickedItemEntity pickedItemEntity){
        return PickedItem.builder()
                .id(pickedItemEntity.getId())
                .backlogItemId(pickedItemEntity.getBacklogItem().getId())
                .SprintId(pickedItemEntity.getSprintEntity().getId())
                .build();
    }

    private static Sprint mapEntityToSprint(SprintEntity sprintEntity){
        return Sprint.builder()
                .id(sprintEntity.getId())
                .description(sprintEntity.getDescription())
                .current(sprintEntity.getCurrent())
                .finished(sprintEntity.getFinished())
                .inPlaning(sprintEntity.getInPlaning())
                .startTime(sprintEntity.getStartTime().toString())
                .endTime(sprintEntity.getEndTime().toString())
                .projectId(sprintEntity.getProject().getId())
                .backlogId(sprintEntity.getBacklog().getId())
                .build();
    }

    private static Task mapEntityToTask(TaskEntity taskEntity){
//       if (taskEntity.getSprintParticipant().getId() != null)
//        return Task.builder()
//                .id(taskEntity.getId())
//                .name(taskEntity.getName())
//                .description(taskEntity.getDescription())
//                .status(taskEntity.getStatus())
//                .sprintId(taskEntity.getSprint().getId())
//                .pickedItemId(taskEntity.getPickedItem().getId())
//                .sprintParticipantId(taskEntity.getSprintParticipant().getId())
//                .build();


        Task task = Task.builder()
                .id(taskEntity.getId())
                .name(taskEntity.getName())
                .description(taskEntity.getDescription())
                .status(taskEntity.getStatus())
                .sprintId(taskEntity.getSprint().getId())
                .pickedItemId(taskEntity.getPickedItem().getId())
                .expectedHoursToFinish(taskEntity.getExpectedHoursToFinish())

//                .sprintParticipantId(taskEntity.getSprintParticipant().getId())
                .build();
        if (taskEntity.getFinishedAt() != null)
            task.setFinishedAt(taskEntity.getFinishedAt().toString());

        return task;




    }

    private TaskEntity mapTaskToEntity(Task task){
        return TaskEntity.builder()
                .name(task.getName())
                .description(task.getDescription())
                .status(ETaskStatus.FREE)
                .sprint( sprintRepository.findById(task.getSprintId()).get())
//                .sprintParticipant() later
                .expectedHoursToFinish(task.getExpectedHoursToFinish())
                .pickedItem(pickedItemRepository.findBySprintEntityAndBacklogItem(
                 sprintRepository.findById(task.getSprintId()).get() ,
                 backlogItemRepository.findById(task.getPickedItemId()).get()
                ))
                .build();
    }


    private static BacklogItem mapEntityToBacklogItem(BacklogItemEntity backlogItemEntity){
        return BacklogItem.builder()
                .id(backlogItemEntity.getId())
                .name(backlogItemEntity.getName())
                .type(backlogItemEntity.getType())
                .priority(backlogItemEntity.getPriority())
                .release(backlogItemEntity.getReleaseV())
                .story(backlogItemEntity.getStory())
                .details(backlogItemEntity.getDetails())
                .description(backlogItemEntity.getDescription())
                .backlogId(backlogItemEntity.getBacklog().getId())
                .build();
    }



}
