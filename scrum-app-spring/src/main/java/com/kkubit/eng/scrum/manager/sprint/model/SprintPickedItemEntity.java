package com.kkubit.eng.scrum.manager.sprint.model;

import com.kkubit.eng.scrum.manager.project.model.BacklogItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
* Specify which items are done in sprint
*
* */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintPickedItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    BacklogItemEntity backlogItem;
    @ManyToOne
    SprintEntity sprintEntity;

}
