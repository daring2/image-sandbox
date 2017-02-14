package com.gitlab.daring.image.util;

import org.junit.Test;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ExtStringUtilsTest {

	@Test
	public void testSplitAndTrim() {
		checkSplitAndTrim("p1, p2, p3", ", ", "p1", "p2", "p3");
		checkSplitAndTrim(" p1 , , p2 ", ",", "p1", "p2");
		checkSplitAndTrim("p1;p2", ";", "p1", "p2");
	}

	void checkSplitAndTrim(String str, String sep, String... ss) {
		assertEquals(asList(ss), splitAndTrim(str, sep).toList());
	}

}