package com.kkubit.eng.scrum.manager.sprint.database;

import com.kkubit.eng.scrum.manager.sprint.model.DailySprintTimePreferenceEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintTimePreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.LinkedList;
import java.util.Optional;

@Repository
public interface DailySprintTimePreferenceRepository extends JpaRepository<DailySprintTimePreferenceEntity, Integer> {

    LinkedList<DailySprintTimePreferenceEntity> findAllByPreference(SprintTimePreferenceEntity preference);
    Optional<DailySprintTimePreferenceEntity> findByPreferenceAndOnDay(SprintTimePreferenceEntity preference, Date onDay);
    @Transactional
    void deleteByPreference(SprintTimePreferenceEntity preference);

}
