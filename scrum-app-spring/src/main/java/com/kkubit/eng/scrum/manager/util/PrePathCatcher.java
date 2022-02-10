package com.kkubit.eng.scrum.manager.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component(value="prePathCatcher")
@RequiredArgsConstructor
public class PrePathCatcher {
    private final HttpServletRequest httpServletRequest;

    public Object getPathVariableByName(String name) {
        final Map pathVariables = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return pathVariables.get(name);
    }


}

