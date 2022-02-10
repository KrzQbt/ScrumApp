package com.kkubit.eng.scrum.manager.sprint.service.impl;

import com.kkubit.eng.scrum.manager.authentication.database.UserRepository;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import com.kkubit.eng.scrum.manager.authentication.security.services.UserDetailsImpl;
import com.kkubit.eng.scrum.manager.sprint.api.dto.DetailedSprintParticipant;
import com.kkubit.eng.scrum.manager.sprint.api.dto.SprintParticipant;
import com.kkubit.eng.scrum.manager.sprint.database.SprintParticipantRepository;
import com.kkubit.eng.scrum.manager.sprint.database.SprintPickedItemRepository;
import com.kkubit.eng.scrum.manager.sprint.database.SprintRepository;
import com.kkubit.eng.scrum.manager.sprint.database.TaskRepository;
import com.kkubit.eng.scrum.manager.sprint.model.SprintEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintParticipantEntity;
import com.kkubit.eng.scrum.manager.sprint.model.SprintPickedItemEntity;
import com.kkubit.eng.scrum.manager.sprint.model.TaskEntity;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ESprintDeleteRole;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ESprintEditRole;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ESprintRole;
import com.kkubit.eng.scrum.manager.sprint.service.ISprintParticipationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value="sprintParticipationService")
@AllArgsConstructor
public class SprintParticipationServiceImpl implements ISprintParticipationService {

    private final SprintRepository sprintRepository;
    private final SprintParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final SprintPickedItemRepository pickedItemRepository;
    private final TaskRepository taskRepository;

    /*
     * Methods for pre-authorization, which determine if user is entitled to access api
     * */
    @Override
    public boolean isUserSprintParticipant(UserDetailsImpl userDetails,Object sprintId) {
        if (sprintRepository.existsById(Long.parseLong(sprintId.toString()))
                && userRepository.existsById(userDetails.getId())
        ) {
            System.out.println("sprint party");
            return participantRepository.findBySprintAndUser(
                    sprintRepository.findById(Long.parseLong(sprintId.toString())).get(),
                    userRepository.findById(userDetails.getId()).get()
                    ).isPresent();
        }

        return false;
    }

    public boolean isUserPickedViewer(UserDetailsImpl userDetails,Object pickedId){

        if (pickedItemRepository.findById(Long.parseLong(pickedId.toString())).isPresent()){

            SprintPickedItemEntity pickedItem =
                    pickedItemRepository.findById(Long.parseLong(pickedId.toString())).get();

            return isUserSprintParticipant(userDetails,pickedItem.getSprintEntity().getId());

        }
        return false;
    }

    public boolean isUserBoardEditor(UserDetailsImpl userDetails,Object taskId){
        if (taskRepository.findById(Long.parseLong(taskId.toString()))
                .isPresent()

        ){
            TaskEntity task =
                    taskRepository.findById(Long.parseLong(taskId.toString())).get();

            return isUserSprintParticipant(userDetails,task.getSprint().getId());
        }

        return false;
    }

    public boolean isUserAllowedToDeleteBoard(UserDetailsImpl userDetails,Object taskId){
        if (taskRepository.findById(Long.parseLong(taskId.toString()))
                .isPresent()

        ){
            TaskEntity task =
                    taskRepository.findById(Long.parseLong(taskId.toString())).get();

            return isUserAllowedToDeleteSprint(userDetails,task.getSprint().getId());
        }

        return false;
    }




    public boolean isUserSprintEditor(UserDetailsImpl userDetails,Object sprintId) {
        if (sprintRepository.existsById(Long.parseLong(sprintId.toString()))
                && userRepository.existsById(userDetails.getId())
        ) {

            if (participantRepository.findBySprintAndUser(
                    sprintRepository.findById(Long.parseLong(sprintId.toString())).get(),
                    userRepository.findById(userDetails.getId()).get()
            ).isPresent()){

                SprintParticipantEntity participant =
                        participantRepository.findBySprintAndUser(
                                sprintRepository.findById(Long.parseLong(sprintId.toString())).get(),
                                userRepository.findById(userDetails.getId()).get()
                        ).get();

                       for (ESprintEditRole role : ESprintEditRole.values()){
                           if (participant.getRole().toString()
                                   .equals(role.toString())){
                               return true;
                           }
                       }
            }

        }

        return false;
    }







    public boolean isUserAllowedToDeleteSprint(UserDetailsImpl userDetails,Object sprintId) {
        if (sprintRepository.existsById(Long.parseLong(sprintId.toString()))
                && userRepository.existsById(userDetails.getId())
        ) {

            if (participantRepository.findBySprintAndUser(
                    sprintRepository.findById(Long.parseLong(sprintId.toString())).get(),
                    userRepository.findById(userDetails.getId()).get()
            ).isPresent()){

                SprintParticipantEntity participant =
                        participantRepository.findBySprintAndUser(
                                sprintRepository.findById(Long.parseLong(sprintId.toString())).get(),
                                userRepository.findById(userDetails.getId()).get()
                        ).get();

                for (ESprintDeleteRole role : ESprintDeleteRole.values()){
                    if (participant.getRole().toString()
                            .equals(role.toString())){
                        return true;
                    }
                }

            }

        }

        return false;


    }

    @Override
    public boolean isUserRoleSufficient(UserDetailsImpl userDetails) {
        return false;
    }


    /*
     * methods to get all participants
     * */
    @Override
    public List<DetailedSprintParticipant> getAllSprintParticipants(Long sprintId) {

        if(sprintRepository.findById(sprintId).isEmpty())
            return null;

        SprintEntity sprintEntity = sprintRepository.findById(sprintId).get();

        // if there are no participants ret null
        if (participantRepository.findAllBySprint(sprintEntity).isEmpty())
            return null;

        return participantRepository.findAllBySprint(sprintEntity)
                .stream()
                .map(SprintParticipationServiceImpl::mapEntityToDetailedParticipant)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    public void setNewSprintRole(SprintParticipant sprintParticipant) {
        if (sprintRepository.findById(
                sprintParticipant.getSprintId()).isEmpty()
                &&
                userRepository.findById(
                        sprintParticipant.getUserId()).isEmpty()
        )
            return;


        SprintEntity sprintEntity =sprintRepository.findById(
                sprintParticipant.getSprintId()).get();

        UserEntity userEntity = userRepository.findById(
                sprintParticipant.getUserId()).get();

        // if user was not participant, they will be added with role
        if (participantRepository.findBySprintAndUser(sprintEntity,userEntity).isEmpty()){
            participantRepository.save(SprintParticipantEntity.builder()
                    .sprint(sprintEntity)
                    .user(userEntity)
                    .role(ESprintRole.valueOf(sprintParticipant.getRole()))
                    .build()
            );
            return;
        }

        // there is participation so it is retrieved, role is changed and saved again
        SprintParticipantEntity participantEntity = participantRepository.findBySprintAndUser(sprintEntity,userEntity).get();

        participantEntity.setRole(ESprintRole.valueOf(sprintParticipant.getRole()));
        participantRepository.save(participantEntity);

    }

    @Override
    public void removeParticipantFromSprint(Long participationId) {

        participantRepository.deleteById(participationId);
    }

    @Override
    public List<String> getPossibleProjectRoles() {
        return Stream.of(ESprintRole.values())
                .map(ESprintRole::name)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    private static DetailedSprintParticipant mapEntityToDetailedParticipant(SprintParticipantEntity participantEntity){
        return DetailedSprintParticipant.builder()
                .id(participantEntity.getId())
                .sprintId(participantEntity.getSprint().getId())
                .userId(participantEntity.getUser().getId())
                .username(participantEntity.getUser().getUsername())
                .role(participantEntity.getRole().name())
                .build();
    }
}
