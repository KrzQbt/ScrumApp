package com.kkubit.eng.scrum.manager.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
* just storage for stories to copy to backlog item, many to one with project
* */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String story;

    @ManyToOne
    ProjectEntity project;

}
