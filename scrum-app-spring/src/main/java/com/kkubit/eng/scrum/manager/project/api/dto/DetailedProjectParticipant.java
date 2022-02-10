package com.kkubit.eng.scrum.manager.project.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kkubit.eng.scrum.manager.authentication.model.ESystemRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = DetailedProjectParticipant.DetailedProjectParticipantBuilder.class)
public class DetailedProjectParticipant {
    Integer id;
    Long projectId;

    Long userId;
    String username;

    String role;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DetailedProjectParticipantBuilder{

    }
}
