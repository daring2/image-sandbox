package com.gitlab.daring.sandbox.image.processor;

public abstract class BaseImageProcessor implements ImageProcessor {

	protected final String[] args;

	public BaseImageProcessor(String[] args) {
		this.args = args;
	}

}
