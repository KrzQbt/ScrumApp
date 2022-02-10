package com.kkubit.eng.scrum.manager.sprint.database;

import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintParticipantEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintTimePreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;

@Repository
public interface SprintTimePreferenceRepository extends JpaRepository<SprintTimePreferenceEntity,Long> {

    Optional<SprintTimePreferenceEntity> findBySprintParticipant(SprintParticipantEntity sprintParticipantEntity);
    LinkedList<SprintTimePreferenceEntity> findAllBySprintEntity(SprintEntity sprintEntity);
    @Transactional
    void deleteBySprintEntity(SprintEntity sprintEntity);


}
