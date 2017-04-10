package com.gitlab.daring.image.command.template

internal enum class MatchMethod {

    SQDIFF, SQDIFF_NORMED, CCORR, CCORR_NORMED, CCOEFF, COEFF_NORMED;

    val isMinBest: Boolean
        get() = this == SQDIFF || this == SQDIFF_NORMED

}