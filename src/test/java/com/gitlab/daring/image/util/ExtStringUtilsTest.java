package com.gitlab.daring.image.util;

import org.junit.Test;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ExtStringUtilsTest {

	@Test
	public void testSplitAndTrim() {
		assertEquals(asList("p1", "p2", "p3"), splitAndTrim("p1, p2, p3", ", "));
		assertEquals(asList("p1", "p2"), splitAndTrim(" p1 , , p2 ", ","));
		assertEquals(asList("p1", "p2"), splitAndTrim("p1;p2", ";"));
	}

}