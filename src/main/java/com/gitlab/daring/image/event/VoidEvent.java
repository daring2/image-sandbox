package com.gitlab.daring.image.event;

public class VoidEvent extends ValueEvent<Void> {

	public void onFire(Runnable l) {
		addListener(e -> l.run());
	}

	public void fire() {
		fire(null);
	}

}
