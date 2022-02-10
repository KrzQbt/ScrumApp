package com.kkubit.eng.scrum.manager.sprint.database;

import com.kkubit.eng.scrum.manager.project.model.BacklogItemEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintPickedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;

@Repository
public interface SprintPickedItemRepository extends JpaRepository<SprintPickedItemEntity,Long> {

    LinkedList<SprintPickedItemEntity> findAllBySprintEntity(SprintEntity sprintEntity);
    @Transactional
    void deleteBySprintEntity(SprintEntity sprintEntity);
    SprintPickedItemEntity findBySprintEntityAndBacklogItem(SprintEntity sprintEntity, BacklogItemEntity backlogItemEntity);



}
