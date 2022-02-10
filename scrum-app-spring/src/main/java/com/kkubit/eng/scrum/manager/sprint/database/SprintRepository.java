package com.kkubit.eng.scrum.manager.sprint.database;

import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface SprintRepository extends JpaRepository<SprintEntity,Long> {
    LinkedList<SprintEntity> findAllByProject(ProjectEntity projectEntity);

}
