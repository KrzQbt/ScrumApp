package com.kkubit.eng.scrum.manager.project.service;

import com.kkubit.eng.scrum.manager.project.api.action.BacklogPatchActions;
import com.kkubit.eng.scrum.manager.project.api.dto.Backlog;
import com.kkubit.eng.scrum.manager.project.api.dto.BacklogItem;

import java.util.LinkedList;

public interface IBacklogService {
    Backlog getBacklog(Long backlogId);
    LinkedList<BacklogItem> getBacklogItems(Long backlogId);
    LinkedList<BacklogItem> getSortedBacklogItems(Long backlogId);
    void updateBacklogItemList(LinkedList<BacklogItem> list);
    void executeBacklogPatchAction(Long backlogId, BacklogPatchActions bpa);
    void setBacklogAsCurrent(Long backlogId);
    void deleteSingleBacklogItem(Long id);
    void saveNewBacklog(Long projectId, Backlog backlog);
    void saveNewBacklogItem(Long backlogId, BacklogItem backlogItem);
    void deleteBacklog(Long backlogId);


    }
