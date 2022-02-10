package com.kkubit.eng.scrum.manager.project.service.impl;

import com.kkubit.eng.scrum.manager.project.api.action.BacklogPatchActions;
import com.kkubit.eng.scrum.manager.project.api.dto.Backlog;
import com.kkubit.eng.scrum.manager.project.api.dto.BacklogItem;
import com.kkubit.eng.scrum.manager.project.database.BacklogItemRepository;
import com.kkubit.eng.scrum.manager.project.database.BacklogRepository;
import com.kkubit.eng.scrum.manager.project.database.ProjectRepository;
import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;
import com.kkubit.eng.scrum.manager.project.model.BacklogItemEntity;
import com.kkubit.eng.scrum.manager.project.service.IBacklogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class BacklogService implements IBacklogService {
    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final BacklogItemRepository backlogItemRepository;

    @Override
    public void saveNewBacklogItem(Long backlogId, BacklogItem backlogItem) {
        if (backlogRepository.findById(backlogId).isEmpty())
            return;
        BacklogEntity backlog =
                backlogRepository.findById(backlogId).get();

        backlogItemRepository.save(
                BacklogItemEntity.builder()
                        .name(backlogItem.getName())
                        .type(backlogItem.getType())
                        .description(backlogItem.getDescription())
                        .story(backlogItem.getStory())
                        .priority(Integer.MAX_VALUE)
                        // max value, this value gets changed at first grroming when priority gets  set at index
                        .releaseV(backlogItem.getRelease())
                        .backlog(backlog)
                        .build()
        );



    }

    @Override
    public void saveNewBacklog(Long projectId, Backlog backlog) {
        if (projectRepository.findById(projectId).isEmpty())
            return;

            BacklogEntity newBacklogEntity = backlogRepository.save(
                    BacklogEntity.builder()
                    .description(backlog.getDescription())
                    .current(false)
                    .project(projectRepository.findById(projectId).get())
                    .build());

            // if there is no given backlogId to copy from, there is no copy done
            if(backlog.getCopyItemsFromBacklogId() == null)
                return;

            if (backlogRepository.findById(backlog.getCopyItemsFromBacklogId()).isEmpty())
                return;

            BacklogEntity backlogToCopyFrom =
                    backlogRepository.findById(backlog.getCopyItemsFromBacklogId()).get();

            LinkedList<BacklogItemEntity> backlogItemsToCopy = backlogItemRepository.findAllByBacklog(backlogToCopyFrom);

            backlogItemsToCopy.forEach(
                    item -> {
                        item.setBacklog(newBacklogEntity);
                    }
            );

            backlogItemRepository.saveAll(backlogItemsToCopy);







    }

    @Override
    public Backlog getBacklog(Long backlogId){
        if (backlogRepository.findById(backlogId).isEmpty())
            return null;
        else
            return BacklogService.mapEntityToBacklog(backlogRepository.findById(backlogId).get());
    }

    @Override
    public LinkedList<BacklogItem> getBacklogItems(Long backlogId){
        if(backlogRepository.findById(backlogId).isEmpty())
            return null;
        else
            return backlogItemRepository
                    .findAllByBacklog(backlogRepository.findById(backlogId).get())
                    .stream()
                    .map(BacklogService::mapEntityToBacklogItem)
                    .collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    public LinkedList<BacklogItem> getSortedBacklogItems(Long backlogId){
        if(backlogRepository.findById(backlogId).isEmpty())
            return null;
        else{
            return backlogItemRepository
                    .findAllByBacklog(backlogRepository.findById(backlogId).get())
                    .stream()
                    .map(BacklogService::mapEntityToBacklogItem)
                    .sorted(Comparator.comparingLong(
                            BacklogItem::getPriority))
                    .collect(Collectors.toCollection(LinkedList::new));
        }

    }

    @Override
    public void updateBacklogItemList(LinkedList<BacklogItem> list){

        backlogItemRepository.saveAll(
                list.stream()
                        .map( item ->{
                            if (backlogRepository.findById(item.getBacklogId()).isEmpty())
                                return null;
                            else
                                return mapBacklogItemToEntity(item,backlogRepository.findById(item.getBacklogId()).get());
                        })
                        .collect(Collectors.toCollection(LinkedList::new))

        );
    }

    /*
     * patch actions method checks if fields are true
     * if they are, each action is executed
     * more actions can be added in new fields
     */
    @Override
    public void executeBacklogPatchAction(Long backlogId, BacklogPatchActions bpa){

        if (bpa.getCurrent() == true ) {
            setBacklogAsCurrent(backlogId);

        }
    }
    /*
     * there is only one currently used backlog in each project (latest one)
     * purpose of this method is to set current backlog to false
     * and to set desired backlog current status as true
     *
     * */
    // there is only one currently used backlog in each project (latest one)
    // method finds current backlog in DB for this project, sets current to false
    @Override
    public void setBacklogAsCurrent(Long backlogId){
        if (backlogRepository.findById(backlogId).isEmpty())
            System.out.println("cant set current, non existent backlog");


        else {
            BacklogEntity newCurrentBacklog = backlogRepository
                    .findById(backlogId).get();
            // retrieved what is Id,
            // searching for all backlogs that are current
            LinkedList<BacklogEntity> toChangeCurrentList = backlogRepository.findAllByProjectAndCurrent( newCurrentBacklog.getProject(),true
            );
            //changing undesired backlogs cuirrent status to false and saving
            toChangeCurrentList.forEach( backlog -> {
                System.out.println(backlog);
                backlog.setCurrent(false);
                System.out.println(backlog);
                backlogRepository.save(backlog);
            });

            //changing status to current and saving desired backlog
            newCurrentBacklog.setCurrent(true);
            backlogRepository.save(newCurrentBacklog);

        }
    }

    @Override
    public void deleteSingleBacklogItem(Long id){
        backlogItemRepository.deleteById(id);
    }

    @Override
    public void deleteBacklog(Long backlogId) {
        if (backlogRepository.findById(backlogId).isEmpty())
            return;
        BacklogEntity backlogEntity =
                backlogRepository.findById(backlogId).get();
        backlogItemRepository.deleteByBacklog(backlogEntity);

    }

    private static Backlog mapEntityToBacklog(BacklogEntity backlogEntity){

        return Backlog.builder()
                .id(backlogEntity.getId())
                .description(backlogEntity.getDescription())
                .current(backlogEntity.getCurrent())
                .projectId(backlogEntity.getProject().getId())
                .build();
    }

    private static BacklogItemEntity mapBacklogItemToEntity(BacklogItem backlogItem, BacklogEntity backlogEntity){

        return BacklogItemEntity.builder()
                .id(backlogItem.getId())
                .name(backlogItem.getName())
                .type(backlogItem.getType())
                .priority(backlogItem.getPriority())
                .releaseV(backlogItem.getRelease())
                .story(backlogItem.getStory())
                .details(backlogItem.getDetails())
                .description(backlogItem.getDescription())
                .backlog(backlogEntity)
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
