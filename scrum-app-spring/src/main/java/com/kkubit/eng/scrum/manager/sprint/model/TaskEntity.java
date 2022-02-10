package com.kkubit.eng.scrum.manager.sprint.model;

import com.kkubit.eng.scrum.manager.project.model.BacklogItemEntity;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ETaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    Integer expectedHoursToFinish;
    Date finishedAt;

    @Enumerated(EnumType.STRING)
    ETaskStatus status;

    @ManyToOne
    SprintEntity sprint;
    @ManyToOne
    SprintPickedItemEntity pickedItem;
    @ManyToOne
    SprintParticipantEntity sprintParticipant; // person who is handling picked task


}
