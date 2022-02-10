package com.kkubit.eng.scrum.manager.sprint.service;

import com.kkubit.eng.scrum.manager.sprint.api.dto.BurndownWrapper;
import com.kkubit.eng.scrum.manager.sprint.api.dto.DailyTimePreference;
import com.kkubit.eng.scrum.manager.sprint.api.dto.TimePreferenceWrapper;

import java.security.Principal;
import java.util.List;

public interface ITimeManagementService {

    TimePreferenceWrapper getUsersTimePreference(Long sprintId, Principal principal);
    void saveDailyPreference(Long sprintId, Principal principal, DailyTimePreference dailyTimePreference);
    void deleteDailyPreferenceById(Integer id);
    List<TimePreferenceWrapper> getAllSprintParticipantPreferences(Long sprintId);
    BurndownWrapper getBurndownData(Long sprintId);
}
