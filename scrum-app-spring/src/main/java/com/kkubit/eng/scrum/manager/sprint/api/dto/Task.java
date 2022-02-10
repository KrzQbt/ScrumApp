package com.kkubit.eng.scrum.manager.sprint.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kkubit.eng.scrum.manager.sprint.model.enums.ETaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = Task.TaskBuilder.class)
public class Task {

    Long id;
    String name;
    String description;
    @Enumerated(EnumType.STRING)
    ETaskStatus status;
    Long sprintId;
    Long pickedItemId;
    Long sprintParticipantId; // owner
    String finishedAt;
    Integer expectedHoursToFinish;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TaskBuilder{

    }
}
