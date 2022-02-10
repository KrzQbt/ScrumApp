package com.kkubit.eng.scrum.manager.sprint.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = BurndownWrapper.BurndownWrapperBuilder.class)
public class BurndownWrapper {

    String sprintStartDate;
    String sprintEndDate;
    List<TimePreferenceWrapper> preferenceWrapperList;
    LinkedList<Task> taskList;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BurndownWrapperBuilder{

    }
}
