package com.kkubit.eng.scrum.manager.sprint.database;

import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectParticipantEntity;
import com.kkubit.eng.scrum.manager.sprint.api.dto.Sprint;
import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;

@Repository
public interface SprintParticipantRepository extends JpaRepository<SprintParticipantEntity,Long> {

    LinkedList<SprintParticipantEntity> findAllBySprint(SprintEntity sprintEntity);
    Optional<SprintParticipantEntity> findBySprintAndUser(SprintEntity sprint, UserEntity user);
    LinkedList<SprintParticipantEntity> findAllByUser(UserEntity user);
    @Transactional
    void deleteBySprint(SprintEntity sprint);

}
