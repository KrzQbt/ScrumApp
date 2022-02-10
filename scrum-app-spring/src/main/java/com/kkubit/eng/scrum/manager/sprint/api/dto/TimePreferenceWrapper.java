package com.kkubit.eng.scrum.manager.sprint.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.LinkedList;


@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = TimePreferenceWrapper.TimePreferenceWrapperBuilder.class)
public class TimePreferenceWrapper {

    Long id;
    Long participantId;
    String username;
    LinkedList<DailyTimePreference> dailyTimePreferences;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TimePreferenceWrapperBuilder{ }


}
