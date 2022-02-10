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
@JsonDeserialize(builder = BacklogItem.BacklogItemBuilder.class)
public class BacklogItem {

    Long id;
    String name;
    String type;
    String description;
    String story;
    String details;
    String release;
    Integer priority;

    Long backlogId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BacklogItemBuilder {

    }

}
