package com.kkubit.eng.scrum.manager.sprint.api;

import com.kkubit.eng.scrum.manager.sprint.api.dto.BurndownWrapper;
import com.kkubit.eng.scrum.manager.sprint.api.dto.DailyTimePreference;
import com.kkubit.eng.scrum.manager.sprint.api.dto.TimePreferenceWrapper;
import com.kkubit.eng.scrum.manager.sprint.service.ITimeManagementService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class SprintTimeReportController {

    private final ITimeManagementService timeManagementService;

    @GetMapping("sprint/{sid}/report")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    public List<TimePreferenceWrapper> getAllSprintParticipantsPreference(@PathVariable Long sid){

        return timeManagementService.getAllSprintParticipantPreferences(sid);
    }

    @GetMapping("sprint/{sid}/time")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    public TimePreferenceWrapper getMyPreferencesForSprint(@PathVariable Long sid, Principal principal){

        return timeManagementService.getUsersTimePreference(sid,principal);
    }

    @PostMapping("sprint/{sid}/time")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    public void setMyDailyPreference(@PathVariable Long sid, Principal principal, @RequestBody DailyTimePreference dailyTimePreference){

        timeManagementService.saveDailyPreference(sid, principal, dailyTimePreference);

    }


    // preauthorize with principal check TODO
    @DeleteMapping("daily/{did}")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    public void deleteDailyPreference(@PathVariable Integer did){

        timeManagementService.deleteDailyPreferenceById(did);
    }

    @GetMapping("/sprint/{sid}/burndown")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    BurndownWrapper getBurndownData(@PathVariable Long sid){
        return timeManagementService.getBurndownData(sid);
    }






}
