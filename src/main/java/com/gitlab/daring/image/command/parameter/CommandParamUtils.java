package com.gitlab.daring.image.command.parameter;

import one.util.streamex.StreamEx;

import java.util.Collection;
import java.util.Map;

public class CommandParamUtils {

    public static Map<String, Object> buildParamConfig(Collection<CommandParam<?>> params) {
        return StreamEx.of(params)
            .filter(p -> p.configPath != null)
            .toMap(p -> p.configPath, CommandParam::configValue);
    }

    private CommandParamUtils() {
    }
}
