package com.gitlab.daring.javacv;

import org.bytedeco.javacpp.IntPointer;

class JavaCvUtils {

	static IntPointer intPointer(int... vs) {
		return new IntPointer(vs);
	}

	private JavaCvUtils() {
	}

}