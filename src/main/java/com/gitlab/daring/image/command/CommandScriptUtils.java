package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import static org.apache.commons.lang3.StringUtils.join;

public class CommandScriptUtils {

    public static ScriptCommand parseScript(String script) {
        return CommandRegistry.Instance.parseScript(script);
    }

    public static void runScript(CommandEnv env, String script) {
        parseScript(script).execute(env);
    }

    public static String commandString(String cmd, Object... args) {
        return cmd + "(" + join(args, ", ") + ");";
    }

    public static Mat runCommand(CommandEnv env, String cmd, Object... args) {
        runScript(env, commandString(cmd, args));
        return env.getMat();
    }

    private CommandScriptUtils() {
    }

}