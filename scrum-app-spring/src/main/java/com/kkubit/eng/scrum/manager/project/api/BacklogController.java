package com.kkubit.eng.scrum.manager.project.api;


import com.kkubit.eng.scrum.manager.project.api.action.BacklogPatchActions;
import com.kkubit.eng.scrum.manager.project.api.dto.Backlog;
import com.kkubit.eng.scrum.manager.project.api.dto.BacklogItem;
import com.kkubit.eng.scrum.manager.project.service.IBacklogService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class BacklogController {
    private final IBacklogService backlogService;


    @GetMapping("/backlog/info/{bid}")
    @PreAuthorize("@participationService.isUserBacklogViewer(principal,@prePathCatcher.getPathVariableByName('bid'))")
    Backlog getBacklog(@PathVariable Long bid){
        return backlogService.getBacklog(bid);
    }

    @PreAuthorize("@participationService.isUserProjectParticipant(principal,@prePathCatcher.getPathVariableByName('pid'))")
    @PostMapping("project/{pid}/backlog")
    void createNewBacklogForProject(@PathVariable Long pid, @RequestBody Backlog backlog){
        backlogService.saveNewBacklog(pid,backlog);
    }

//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/backlog/{bid}")
    @PreAuthorize("@participationService.isUserBacklogViewer(principal,@prePathCatcher.getPathVariableByName('bid'))")
    LinkedList<BacklogItem> getSortedBacklogItems(@PathVariable Long bid){


        return backlogService.getSortedBacklogItems(bid);
    }


    @PutMapping("/backlog/{bid}")
    @PreAuthorize("@participationService.isUserBacklogEditor(principal,@prePathCatcher.getPathVariableByName('bid'))")
    public void updateBacklog(@RequestBody LinkedList<BacklogItem> list){

        backlogService.updateBacklogItemList(list);

    }

    @PatchMapping("/backlog/{bid}")
    @PreAuthorize("@participationService.isUserBacklogEditor(principal,@prePathCatcher.getPathVariableByName('bid'))")
    public void backlogPropertiesUpdate(@RequestBody BacklogPatchActions bpa, @PathVariable Long bid){
        System.out.println("patch");
        backlogService.executeBacklogPatchAction(bid,bpa);
    }



    @PostMapping("/backlog/{bid}/item")
    @PreAuthorize("@participationService.isUserBacklogEditor(principal,@prePathCatcher.getPathVariableByName('bid'))")
    public void createNewItem(@PathVariable Long bid, @RequestBody BacklogItem backlogItem){
        backlogService.saveNewBacklogItem(bid,backlogItem);
//        System.out.println(backlogItem);
    }

    @DeleteMapping("/item/{iid}")
    @PreAuthorize("@participationService.isUserAllowedToDeleteBacklogItem(principal,@prePathCatcher.getPathVariableByName('iid'))")
    public void deleteBacklogItem(@PathVariable Long iid){
        backlogService.deleteSingleBacklogItem(iid);
    }


}
