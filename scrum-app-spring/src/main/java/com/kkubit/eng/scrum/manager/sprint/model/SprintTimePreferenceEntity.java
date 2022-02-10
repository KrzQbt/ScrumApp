package com.kkubit.eng.scrum.manager.sprint.model;

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
public class SprintTimePreferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean submitted; // implement final view TODO

    @ManyToOne
    private SprintEntity sprintEntity;

    @OneToOne
    private SprintParticipantEntity sprintParticipant;




}
