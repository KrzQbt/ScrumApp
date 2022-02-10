package com.kkubit.eng.scrum.manager.project.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = NewProject.NewProjectBuilder.class)
public class NewProject {
    String projectName;
    String creatorRole;

    @JsonPOJOBuilder(withPrefix = "")
    public static class NewProjectBuilder{

    }
}
