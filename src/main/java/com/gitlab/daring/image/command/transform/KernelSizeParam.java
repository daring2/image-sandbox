package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Size;

class KernelSizeParam {

	final IntParam p;

	volatile int w;
	volatile Size v;

	KernelSizeParam(BaseCommand cmd, int index) {
		p = cmd.intParam(index, "1-50");
		p.changeEvent.onFire(this::update);
		update();
	}

	void update() {
		w = p.v * 2 + 1;
		v = new Size(w, w);
	}

}