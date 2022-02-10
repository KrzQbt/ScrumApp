package com.kkubit.eng.scrum.manager.project.api.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = BacklogPatchActions.BacklogPatchActionsBuilder.class)
public class BacklogPatchActions {
    Boolean current;
    //space for more actions

    @JsonPOJOBuilder(withPrefix = "")
    public static class BacklogPatchActionsBuilder {

    }
}
