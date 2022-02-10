package com.kkubit.eng.scrum.manager.sprint.service.impl;


import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.User;
import com.kkubit.eng.scrum.manager.authentication.database.UserRepository;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.sprint.api.dto.BurndownWrapper;
import com.kkubit.eng.scrum.manager.sprint.api.dto.DailyTimePreference;
import com.kkubit.eng.scrum.manager.sprint.api.dto.DetailedSprintParticipant;
import com.kkubit.eng.scrum.manager.sprint.api.dto.TimePreferenceWrapper;
import com.kkubit.eng.scrum.manager.sprint.database.DailySprintTimePreferenceRepository;
import com.kkubit.eng.scrum.manager.sprint.database.SprintParticipantRepository;
import com.kkubit.eng.scrum.manager.sprint.database.SprintRepository;
import com.kkubit.eng.scrum.manager.sprint.database.SprintTimePreferenceRepository;
import com.kkubit.eng.scrum.manager.sprint.model.DailySprintTimePreferenceEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintParticipantEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintTimePreferenceEntity;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintParticipationService;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintService;
import com.kkubit.eng.scrum.manager.sprint.service.ITimeManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeManagementServiceImpl implements ITimeManagementService {

    private final SprintTimePreferenceRepository preferenceRepository;
    private final DailySprintTimePreferenceRepository dailyPreferenceRepository;
    private final SprintParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;

    private final ISprintParticipationService participationService;
    private final ISprintService sprintService;

    @Override
    public BurndownWrapper getBurndownData(Long sprintId) {

        if (sprintRepository.findById(sprintId).isEmpty())
            return null; // no such sprint
        SprintEntity sprint =
                sprintRepository.findById(sprintId).get();

        return BurndownWrapper.builder()
                .preferenceWrapperList(getAllSprintParticipantPreferences(sprintId))
                .sprintStartDate(sprint.getStartTime().toString())
                .sprintEndDate(sprint.getEndTime().toString())
                .taskList(sprintService.getTaskListForSprint(sprintId))
                .build();

    }



    @Override
    public List<TimePreferenceWrapper> getAllSprintParticipantPreferences(Long sprintId) {

        List<DetailedSprintParticipant> participantList =
                participationService.getAllSprintParticipants(sprintId);

        LinkedList<TimePreferenceWrapper> preferenceList =
                new LinkedList<>();

        participantList.forEach(
                participant ->{
                    if (participantRepository.findById(participant.getId()).isEmpty())
                        return;

                    SprintParticipantEntity participantEntity =
                            participantRepository.findById(participant.getId()).get();

                    if (preferenceRepository.findBySprintParticipant(participantEntity).isEmpty())
                        return;
                    SprintTimePreferenceEntity preference =
                            preferenceRepository.findBySprintParticipant(participantEntity).get();


                    preferenceList.add(TimePreferenceWrapper.builder()
                            .id(preference.getId())
                            .participantId(participant.getId())
                            .username(participant.getUsername())
                            .dailyTimePreferences(
                                    dailyPreferenceRepository.findAllByPreference(preference)
                                            .stream()
                                            .map(TimeManagementServiceImpl::mapEntityToDailyTimePreference)
                                            .sorted(Comparator.comparing(DailyTimePreference::getDate))
                                            .collect(Collectors.toCollection(LinkedList::new))
                            )
                            .build());

                }
        );

        return preferenceList;
    }


    @Override
    public void deleteDailyPreferenceById(Integer id) {

        dailyPreferenceRepository.deleteById(id);
    }

    @Override
    public TimePreferenceWrapper getUsersTimePreference(Long sprintId, Principal principal) {

        if (userRepository.findByUsername(principal.getName()).isEmpty())
            return null;
        UserEntity user =
                userRepository.findByUsername(principal.getName()).get();

        if (sprintRepository.findById(sprintId).isEmpty())
            return null;
        SprintEntity sprint =
                sprintRepository.findById(sprintId).get();

        if (participantRepository.findBySprintAndUser(sprint,user).isEmpty())
            return null; // user is not participant
        SprintParticipantEntity participant =
                participantRepository.findBySprintAndUser(sprint,user).get();

        if (preferenceRepository.findBySprintParticipant(participant).isEmpty())
            return null;

        SprintTimePreferenceEntity preference =
                preferenceRepository.findBySprintParticipant(participant).get();

        return TimePreferenceWrapper.builder()
                .id(preference.getId())
                .dailyTimePreferences(
                        dailyPreferenceRepository.findAllByPreference(preference)
                                .stream()
                                .map(TimeManagementServiceImpl::mapEntityToDailyTimePreference)
                                .sorted(Comparator.comparing(DailyTimePreference::getDate))
                                .collect(Collectors.toCollection(LinkedList::new))
                )
                .build();
    }



    @Override
    public void saveDailyPreference(Long sprintId, Principal principal, DailyTimePreference dailyTimePreference) {

        //check if user exists
        if (userRepository.findByUsername(principal.getName()).isEmpty())
            return;

        UserEntity user =
                userRepository.findByUsername(principal.getName()).get();

        //check if sprint exists and if user is participant
        if (sprintRepository.findById(sprintId).isEmpty())
            return;
        SprintEntity sprint =
                sprintRepository.findById(sprintId).get();

        if (participantRepository.findBySprintAndUser(sprint,user).isEmpty())
            return; // user is not participant

        SprintParticipantEntity participant =
                participantRepository.findBySprintAndUser(sprint,user).get();

        // check if participant had preference already
        // if not create pref table and add dailies
        if (preferenceRepository.findBySprintParticipant(participant).isEmpty())
            firstMadePreference(sprint,participant,dailyTimePreference);

        //if pref table exists - add dailies
        SprintTimePreferenceEntity preference =
                preferenceRepository.findBySprintParticipant(participant).get();
        nextPreference(preference,dailyTimePreference);

    }



    /*
    * method to create preference and insert first daily preference
    * strategy used when preference is not initiated
    * */
    private void firstMadePreference(SprintEntity sprint, SprintParticipantEntity participant, DailyTimePreference dailyTimePreference){

       SprintTimePreferenceEntity preferenceEntity = preferenceRepository.save(
               SprintTimePreferenceEntity.builder()
                       .sprintEntity(sprint)
                       .sprintParticipant(participant)
                       .submitted(false)
                       .build()
       );

       dailyPreferenceRepository.save(
               DailySprintTimePreferenceEntity.builder()
                       .preference(preferenceEntity)
                       .onDay(Date.valueOf(dailyTimePreference.getDate()))
                       .availableForHours(dailyTimePreference.getAvailableForHours())
                       .build()
       );

    }



    /*
     * method to insert  daily preference
     * strategy used when preference carrier is initiated
     * */
    private void nextPreference(SprintTimePreferenceEntity preference, DailyTimePreference dailyTimePreference){

        // check if there is already pref for the day
    if (dailyPreferenceRepository.findByPreferenceAndOnDay(preference,Date.valueOf(dailyTimePreference.getDate())).isEmpty()
    ) {
        dailyPreferenceRepository.save(
                DailySprintTimePreferenceEntity.builder()
                        .preference(preference)
                        .onDay(Date.valueOf(dailyTimePreference.getDate()))
                        .availableForHours(dailyTimePreference.getAvailableForHours())
                        .build()
        );// there is no pref for the day so its added
    }
    // pref is not empty so its updated
        DailySprintTimePreferenceEntity dailyPreferenceEntity =
                dailyPreferenceRepository.findByPreferenceAndOnDay(preference,Date.valueOf(dailyTimePreference.getDate())).get();

        dailyPreferenceEntity.setAvailableForHours(dailyTimePreference.getAvailableForHours());
        dailyPreferenceRepository.save(dailyPreferenceEntity);

    }



    private static DailyTimePreference mapEntityToDailyTimePreference(DailySprintTimePreferenceEntity entity){
        return DailyTimePreference.builder()
                .id(entity.getId())
                .date(entity.getOnDay().toString())
                .availableForHours(entity.getAvailableForHours())
                .build();

    }


}
