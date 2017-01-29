package com.gitlab.daring.image.util;

import org.junit.Test;

import static com.gitlab.daring.image.util.EnumUtils.findEnum;
import static com.gitlab.daring.image.util.EnumUtilsTest.TestEnum.*;
import static org.junit.Assert.assertEquals;

public class EnumUtilsTest {

	@Test
	public void testFindEnum() {
		checkFindEnum(One, "One");
		checkFindEnum(One, "one");
		checkFindEnum(One, "o");
		checkFindEnum(Two, "two");
		checkFindEnum(Two, "t");
		checkFindEnum(Three, "th");
	}

	void checkFindEnum(TestEnum exp, String name) {
		assertEquals(exp, findEnum(TestEnum.values(), name));
	}

	enum TestEnum { One, Two, Three }

}