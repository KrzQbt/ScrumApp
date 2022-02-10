package com.kkubit.eng.scrum.manager.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BacklogItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String type;
    String description;
    String story;
    String details;
    String releaseV;
    Integer priority;

    @ManyToOne
    BacklogEntity backlog;

}
