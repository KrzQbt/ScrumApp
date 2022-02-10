package com.kkubit.eng.scrum.manager.authentication.api.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kkubit.eng.scrum.manager.project.api.dto.BacklogItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.LinkedList;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))

public class User {

    Long id;
    String username;
    LinkedList<String> roles;
}
