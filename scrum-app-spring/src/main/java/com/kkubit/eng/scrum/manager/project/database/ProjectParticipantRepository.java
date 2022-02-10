package com.kkubit.eng.scrum.manager.project.database;

import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectParticipantRepository extends JpaRepository<ProjectParticipantEntity,Integer> {

    Optional<ProjectParticipantEntity> findByProjectAndUser(ProjectEntity project, UserEntity user);
    LinkedList<ProjectParticipantEntity> findAllByProject(ProjectEntity project);
    LinkedList<ProjectParticipantEntity> findAllByUser(UserEntity user);


}
