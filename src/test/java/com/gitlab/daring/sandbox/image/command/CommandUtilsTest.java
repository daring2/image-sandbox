package com.gitlab.daring.sandbox.image.command;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class CommandUtilsTest {

	@Test
	public void testParseArgs() throws Exception {
		assertEquals(emptyList(), parseArgs(""));
		assertEquals(asList("p1", "p2", "p3"), parseArgs("p1, p2, p3"));
		assertEquals(asList("d1", "d2", "d3"), parseArgs("", "d1", "d2", "d3"));
		assertEquals(asList("p1", "d2", "d3"), parseArgs("p1", "d1", "d2", "d3"));
	}

	List<String> parseArgs(String argStr, String... defArgs) {
		return asList(CommandUtils.parseArgs(argStr, asList(defArgs)));
	}

}