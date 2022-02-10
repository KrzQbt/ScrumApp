package com.kkubit.eng.scrum.manager.sprint.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.sql.Date;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = Sprint.SprintBuilder.class)
public class Sprint {

    Long id;
    String description;
    Boolean current;
    Boolean finished;
    Boolean inPlaning;
    String startTime;
    String endTime;
    Long projectId;
    Long backlogId;


    @JsonPOJOBuilder(withPrefix = "")
    public static class SprintBuilder{

    }
}
