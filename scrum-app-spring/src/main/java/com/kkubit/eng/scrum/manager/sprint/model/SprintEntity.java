package com.kkubit.eng.scrum.manager.sprint.model;


import com.kkubit.eng.scrum.manager.project.model.BacklogEntity;
import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String description;
    Boolean current;
    Boolean finished;
    Boolean inPlaning;
    Date startTime;
    Date endTime;


    @ManyToOne
    ProjectEntity project;
    @ManyToOne
    BacklogEntity backlog;


}
