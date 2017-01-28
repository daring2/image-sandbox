package com.gitlab.daring.sandbox.image.command;

import org.junit.Test;

import static com.gitlab.daring.sandbox.image.command.CommandUtils.parseArgs;
import static com.gitlab.daring.sandbox.image.command.CommandUtils.splitAndTrim;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CommandUtilsTest {

	@Test
	public void testSplitAndTrim() {
		assertEquals(asList("p1", "p2", "p3"), splitAndTrim("p1, p2, p3", ", "));
		assertEquals(asList("p1", "p2"), splitAndTrim(" p1 , , p2 ", ","));
		assertEquals(asList("p1", "p2"), splitAndTrim("p1;p2", ";"));
	}

	@Test
	public void testParseArgs() throws Exception {
		assertArrayEquals(new String[] {}, parseArgs("", emptyList()));
		//TODO implement
	}

}