package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.DMatch;
import org.bytedeco.javacpp.opencv_core.DMatchVector;
import java.util.ArrayList;
import java.util.List;

class MatchUtils {

	static List<DMatch> toList(DMatchVector v) {
		List<DMatch> l = new ArrayList<>();
		for (int i = 0; i < v.size(); i++) l.add(v.get(i));
		return l;
	}

	static DMatchVector toVector(List<DMatch> ms) {
		return new DMatchVector(ms.toArray(new DMatch[] {}));
	}

	static DMatchVector selectBest(DMatchVector v, int n) {
		List<DMatch> ms = toList(v);
		ms.sort((m1, m2) -> m1.lessThan(m2) ? -1 : 1);
		List<DMatch> r = ms.subList(0, Math.min(ms.size(), n));
		return toVector(r);
	}

	private MatchUtils() {
	}

}
