package com.kkubit.eng.scrum.manager.sprint.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = SprintParticipant.SprintParticipantBuilder.class )
public class SprintParticipant {

    Long id;
    Long sprintId;
    Long userId;
    String username;
    String role;

    @JsonPOJOBuilder(withPrefix = "")
    public static class SprintParticipantBuilder{

    }
}
