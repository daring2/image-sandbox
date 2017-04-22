package com.gitlab.daring.image.command;

import one.util.streamex.StreamEx;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.List;
import java.util.function.Consumer;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.lang.Math.max;
import static java.util.Arrays.stream;

public class CommandUtils {

    public static Command newCommand(Consumer<Mat> c) {
        return new SimpleCommand(new String[0]).withFunc(c);
    }

    public static StreamEx<String> splitScript(String script) {
        return splitAndTrim(script, "\n").remove(l -> l.startsWith("//"))
            .flatMap(l -> splitAndTrim(l, ";")).remove(c -> c.startsWith("-"));
    }

    public static String[] parseArgs(String argStr, List<String> defArgs) {
        StreamEx<String> ss = splitAndTrim(argStr, ",");
        return buildArgs(ss.toList(), defArgs);
    }

    public static String[] buildArgs(List<String> args, List<String> defArgs) {
        String[] ps = new String[max(args.size(), defArgs.size())];
        for (int i = 0; i < ps.length; i++)
            ps[i] = i < args.size() ? args.get(i) : defArgs.get(i);
        return ps;
    }

    public static int[] parseIntParams(String... ps) {
        return stream(ps).mapToInt(Integer::parseInt).toArray();
    }

    private CommandUtils() {
    }

}
