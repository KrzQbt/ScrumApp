package com.kkubit.eng.scrum.manager.project.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = UserStory.UserStoryBuilder.class)
public class UserStory {
    Long id;
    String story;
    Long projectId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserStoryBuilder{

    }
}
