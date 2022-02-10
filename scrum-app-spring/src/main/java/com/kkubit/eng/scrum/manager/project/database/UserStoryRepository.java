package com.kkubit.eng.scrum.manager.project.database;

import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import com.kkubit.eng.scrum.manager.project.model.UserStoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStoryEntity, Long> {
    LinkedList<UserStoryEntity> findAllByProject(ProjectEntity projectEntity);
}
