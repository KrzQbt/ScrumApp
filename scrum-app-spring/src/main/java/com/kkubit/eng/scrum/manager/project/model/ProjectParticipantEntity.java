package com.kkubit.eng.scrum.manager.project.model;


import com.kkubit.eng.scrum.manager.authentication.model.ESystemRole;
import com.kkubit.eng.scrum.manager.authentication.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
* role determines scope of participation in project
*
* there is only one role per project
* (contrary to overall system roles, where user can have both role of USER and ADMIN)
* */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private ProjectEntity project;
    @ManyToOne
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private EProjectRole role;


}
