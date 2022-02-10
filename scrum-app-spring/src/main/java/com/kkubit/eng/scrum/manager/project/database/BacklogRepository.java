package com.kkubit.eng.scrum.manager.project.database;

import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface BacklogRepository  extends JpaRepository<BacklogEntity, Long> {
    LinkedList<BacklogEntity> findAllByProject(ProjectEntity project);
    LinkedList<BacklogEntity> findAllByProjectAndCurrent(ProjectEntity project,Boolean current);

}
