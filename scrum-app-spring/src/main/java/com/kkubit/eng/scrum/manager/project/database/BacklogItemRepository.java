package com.kkubit.eng.scrum.manager.project.database;


import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;
import com.kkubit.eng.scrum.manager.project.model.BacklogItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Repository
public interface BacklogItemRepository extends JpaRepository<BacklogItemEntity, Long> {
    LinkedList<BacklogItemEntity> findAllByBacklog(BacklogEntity backlog);
    @Transactional
    void deleteByBacklog(BacklogEntity backlogEntity);
}
