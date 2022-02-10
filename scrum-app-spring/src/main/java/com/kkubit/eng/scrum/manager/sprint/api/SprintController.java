package com.kkubit.eng.scrum.manager.sprint.api;


import com.kkubit.eng.scrum.manager.project.api.dto.BacklogItem;
import com.kkubit.eng.scrum.manager.sprint.api.dto.*;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintService;
import com.kkubit.eng.scrum.manager.sprint.service.StartService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class SprintController {
    private final ISprintService sprintService;
    private final StartService startService;




    @PatchMapping("/sprint/{sid}/copy")
    void copyTasks(){
        /*
        * copy task from sprint x to sprint sid, both marked on patched object based on compatibility of backlogs
        * TODO
        * */
    }


    @GetMapping("/sprint")
    @PreAuthorize("hasRole('USER')")
    LinkedList<Sprint> getMyOngoingSprints(Principal principal){

        return sprintService.getMySprints(principal);
    }

    /*
     * access sprint listing
     * */

    @GetMapping("/project/{pid}/sprint")
    @PreAuthorize("@participationService.isUserProjectParticipant(principal,@prePathCatcher.getPathVariableByName('pid'))")
    public LinkedList<Sprint> getSprintInProject(@PathVariable Long pid){

        return sprintService.getAllSprintsInProject(pid);
    }

    /*
    * api for task board use
    * */

    @GetMapping("/picked/{pid}")
    @PreAuthorize("@sprintParticipationService.isUserPickedViewer(principal,@prePathCatcher.getPathVariableByName('pid'))")
    public BacklogItem getPickedItemInfo(@PathVariable Long pid){

        return sprintService.getBacklogItemByPickedId(pid);
    }


    @GetMapping("/sprint/{sid}/task")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    public LinkedList<Task> getTaskListForSprint(@PathVariable Long sid){

        return sprintService.getTaskListForSprint(sid);

    }

    @PatchMapping("/task/{tid}")
    @PreAuthorize("@sprintParticipationService.isUserBoardEditor(principal,@prePathCatcher.getPathVariableByName('tid'))")
    public void updateTaskStatus(@RequestBody TaskStatusPatch taskStatusPatch){
        sprintService.updateTaskStatus(taskStatusPatch);

    }

    @PutMapping("/task/{tid}")
    @PreAuthorize("@sprintParticipationService.isUserBoardEditor(principal,@prePathCatcher.getPathVariableByName('tid'))")
    public void changeTask(@RequestBody Task task){

        sprintService.updateTask(task);
    }


    /*
    * api for picking backlog items for sprint
    *
    * when list is ready save or update as whole
    * */


    @GetMapping("/sprint/{sid}/picked")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    LinkedList<BacklogItem> getPickedItemsForSprint(@PathVariable Long sid){

          return sprintService.getPickedItems(sid);
    }



    @PostMapping("/sprint/{sid}/picked")
    @PreAuthorize("@sprintParticipationService.isUserSprintEditor(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void sendPickedItemList(@RequestBody LinkedList<BacklogItem> pickedItems,@PathVariable Long sid){

        sprintService.pickItemsForSprint(pickedItems,sid);
    }

    @PutMapping("/sprint/{sid}/picked")
    @PreAuthorize("@sprintParticipationService.isUserSprintEditor(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void updatePickedItemList(@RequestBody LinkedList<BacklogItem> pickedItems,@PathVariable Long sid){
        pickedItems.forEach(System.out::println);
        sprintService.pickItemsForSprint(pickedItems,sid);
    }

    /*
    * api for creating tasks based or not based on backlog items
    *
    * for frontend it would be useful to get task list and alreadyPicked from above methods
    * */

    @PostMapping("/sprint/{sid}/task")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void createTask(@RequestBody Task task){
        sprintService.createTask(task);
    }

    @DeleteMapping("/task/{tid}")
    @PreAuthorize("@sprintParticipationService.isUserAllowedToDeleteBoard(principal,@prePathCatcher.getPathVariableByName('tid'))")
    void deleteTask(@PathVariable Long tid){

        sprintService.deleteTask(tid);
    }


    /*
     * api for sprint creation, execution and settings
     *
     * */

    @GetMapping("/sprint/{sid}")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    Sprint getSprint(@PathVariable Long sid){
        System.out.println(sprintService.getSprint(sid).toString());
        return sprintService.getSprint(sid);
    }

    @PostMapping("/project/{pid}/sprint")
    @PreAuthorize("@participationService.isUserProjectEditor(principal,@prePathCatcher.getPathVariableByName('pid'))")
    void createSprint(@PathVariable Long pid, @RequestBody NewSprintWrapper sprint, Principal principal){

        sprintService.createSprint(pid,sprint, principal.getName());
    }

    @PatchMapping("/sprint/{sid}")
    @PreAuthorize("@sprintParticipationService.isUserSprintEditor(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void changeSprintStatus(@PathVariable Long sid, @RequestBody SprintStatusPatch sprintStatusPatch){

        sprintService.changeSprintStatus(sid,sprintStatusPatch);
    }

    @PutMapping("/sprint/{sid}")
    @PreAuthorize("@sprintParticipationService.isUserSprintEditor(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void updateSprint(@PathVariable Long sid, @RequestBody Sprint sprint){

        sprintService.updateSprint(sid,sprint);
    }

    @DeleteMapping("/sprint/{sid}")
    @PreAuthorize("@sprintParticipationService.isUserAllowedToDeleteSprint(principal,@prePathCatcher.getPathVariableByName('sid'))")
    void deleteSprint(@PathVariable Long sid){

        sprintService.deleteSprint(sid);
    }

    /*
    * api for sprint time preference creation and time availability reports
    *
    * */


    /* method to get already reported hours in particular sprint*/
    @GetMapping("/participant{pid}/sprint{sid}/preference")
    @PreAuthorize("@sprintParticipationService.isUserSprintParticipant(principal,@prePathCatcher.getPathVariableByName('sid'))")
    TimePreferenceWrapper getPreferenceForSprint(@PathVariable Long pid, @PathVariable Long sid){

        return sprintService.getParticipantTimePreferenceForSprint(pid,sid);
    }




    // initiation right side of database for project 1 after initiating with /start
//    @GetMapping("/startsprint")
//    void startSprint(){
//        startService.startSprint();
//    }













}
