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
@JsonDeserialize(builder = NewSprintWrapper.NewSprintWrapperBuilder.class)
public class NewSprintWrapper {

    String description;
    String startTime;
    String endTime;
    Long projectId;
    Long backlogId;
    Long copyFromSprintWithId;
    String sprintRole;

    @JsonPOJOBuilder(withPrefix = "")
    public static class NewSprintWrapperBuilder{

    }
}
