package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_features2d.FlannBasedMatcher;

public class FlannMatcherCommand extends BaseCommand {

    //TODO support all params

    public FlannMatcherCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        env.descriptorMatcher = new FlannBasedMatcher();
    }

}