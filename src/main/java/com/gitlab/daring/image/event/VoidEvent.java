package com.gitlab.daring.image.event;

public class VoidEvent extends ValueEvent<Void> {

	public void fire() {
		fire(null);
	}

}
