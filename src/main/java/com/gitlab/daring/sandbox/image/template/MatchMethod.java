package com.gitlab.daring.sandbox.image.template;

public enum  MatchMethod {

	SQDIFF, SQDIFF_NORMED, CCORR, CCORR_NORMED, CCOEFF, COEFF_NORMED;

	public boolean isMinBest() {
		return this == SQDIFF || this == SQDIFF_NORMED;
	}

}