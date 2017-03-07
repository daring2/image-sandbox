package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Size;

class KernelSizeParam {

	final IntParam p;

	volatile int w;
	volatile Size v;

	KernelSizeParam(BaseCommand cmd) {
		p = cmd.intParam(1,"0-10");
		p.onChange(this::update);
		update();
	}

	void update() {
		w = Math.max(p.v * 2 + 1, 3);
		v = new Size(w, w);
	}

}