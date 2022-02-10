package com.kkubit.eng.scrum.manager.sprint.database;

import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintTimePreferenceEntity;
import com.kkubit.eng.scrum.manager.sprint.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {

    LinkedList<TaskEntity> findAllBySprint(SprintEntity sprintEntity);
    @Transactional
    void deleteBySprint(SprintEntity sprint);
}
