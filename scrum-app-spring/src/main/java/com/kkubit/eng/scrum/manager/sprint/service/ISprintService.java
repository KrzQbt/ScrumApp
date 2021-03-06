package com.kkubit.eng.scrum.manager.sprint.service;

import com.kkubit.eng.scrum.manager.project.api.dto.BacklogItem;
import com.kkubit.eng.scrum.manager.sprint.api.dto.*;

import java.security.Principal;
import java.util.LinkedList;

public interface ISprintService {

    List<Sprint> getMySprints(Principal principal);
    List<Task> getTaskListForSprint(Long sprintId);
    void updateTask(Task task);
    void updateTaskStatus(TaskStatusPatch statusPatch);
    void createTask(Task task);
    void deleteTask(Long id);
    BacklogItem getBacklogItemByPickedId( Long itemId);
    List<BacklogItem> getPickedItems(Long sprintId);
    List<PickedItem> getAlreadyPickedItems(Long sprintId);
    void pickItemsForSprint(LinkedList<BacklogItem> backlogItems, Long sprintId);
    void updateSprintItems(LinkedList<PickedItem> pickedItems);
    Sprint getSprint(Long sprintId);
    List<Sprint> getAllSprintsInProject(Long projectId);
    void createSprint(Long projectId, NewSprintWrapper sprint, String username);
    void changeSprintStatus(Long sprintId, SprintStatusPatch statusPatch);
    void updateSprint(Long sprintId, Sprint sprint);
    void deleteSprint(Long sprintId);
    TimePreferenceWrapper getParticipantTimePreferenceForSprint(Long participantId, Long sprintId);


}
