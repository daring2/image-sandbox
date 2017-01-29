package com.gitlab.daring.image.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;

import static com.gitlab.daring.image.transform.ConvertCommand.Target.Grey;
import static com.gitlab.daring.image.util.EnumUtils.findEnum;
import static com.gitlab.daring.image.util.ImageUtils.buildMat;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

public class ConvertCommand extends BaseCommand {

	final Target target = findEnum(Target.values(), params[0]);

	public ConvertCommand(String... params) {
		super(params);
	}

	@Override
	public void execute(CommandEnv env) {
		if ((env.mat.channels() == 1) != (target == Grey)) {
			int code = target == Grey ? COLOR_BGR2GRAY : COLOR_BGR2GRAY;
			env.mat = buildMat(m -> cvtColor(env.mat, m, code));
		}
	}

	enum Target {Grey, Color}

}
