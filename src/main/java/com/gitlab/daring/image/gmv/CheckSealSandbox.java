package com.gitlab.daring.image.gmv;

import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.sandbox.ImageSandbox;

public class CheckSealSandbox {

	public static void main(String[] args) {
		CommandRegistry cr = CommandRegistry.Instance;
		cr.register("checkSeal", CheckSealCommand::new);
		ImageSandbox.main(args);
	}

}
