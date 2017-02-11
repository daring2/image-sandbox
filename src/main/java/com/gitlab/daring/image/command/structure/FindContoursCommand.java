package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import org.bytedeco.javacpp.opencv_core.MatVector;

import static com.gitlab.daring.image.util.CollectionUtils.mapList;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static org.bytedeco.javacpp.opencv_imgproc.findContours;

public class FindContoursCommand extends BaseCommand {

	final EnumParam<Mode> mode = enumParam(Mode.class, 0, Mode.List);
	final EnumParam<ApproxMethod> method = enumParam(ApproxMethod.class, 1, ApproxMethod.None);

	public FindContoursCommand(String... params) {
		super(params);
	}

	@Override
	public void execute(CommandEnv env) {
		MatVector mv = new MatVector();
		findContours(env.mat, mv, mode.vi(), method.vi() + 1);
		env.contours = mapList(toJava(mv), Contour::new);
	}

	enum Mode { External, List, CComp, Tree, FloodFill }

	enum ApproxMethod { None, Simple, TC89_L1, TC89_KCOS }
	
}
