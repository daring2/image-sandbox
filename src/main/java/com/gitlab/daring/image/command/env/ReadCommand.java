package com.gitlab.daring.image.command.env;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.BooleanParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.io.File;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class ReadCommand extends BaseCommand {

    final StringParam file = stringParam("");
    final EnumParam<ReadFlag> flags = enumParam(ReadFlag.class, ReadFlag.None);
    final StringParam key = stringParam("");
    final BooleanParam cache = boolParam(true);

    volatile Mat fileMat;
    volatile long fileTime;

    public ReadCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        String fn = env.eval(file.v);
        long ft = new File(fn).lastModified();
        if (ft != fileTime || !cache.v) {
            fileMat = imread(fn, flags.vi() + 1);
            fileTime = ft;
        }
        if (key.v.isEmpty()) {
            env.mat = fileMat.clone();
        } else {
            env.putMat(key.v, fileMat);
        }
    }

    enum ReadFlag {None, Grey, Color}

}
