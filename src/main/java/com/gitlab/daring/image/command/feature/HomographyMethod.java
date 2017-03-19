package com.gitlab.daring.image.command.feature;

public enum HomographyMethod {

    LMEDS(4), RANSAC(8), RHO(16);

    public final int code;

    HomographyMethod(int code) { this.code = code; }

}