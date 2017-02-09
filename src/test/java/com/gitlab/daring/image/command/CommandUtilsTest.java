package com.gitlab.daring.image.command;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
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

	@Test
	public void testBuildArgs() throws Exception {
		assertEquals(emptyList(), buildArgs(emptyList(), emptyList()));
		List<String> args = asList("p1", "p2");
		List<String> defArgs = asList("d1", "d2", "d3");
		assertEquals(args, buildArgs(args, emptyList()));
		assertEquals(defArgs, buildArgs(emptyList(), defArgs));
		assertEquals(asList("p1", "d2", "d3"), buildArgs(asList("p1"), defArgs));
	}

	List<String> buildArgs(List<String> args, List<String> defArgs) {
		return asList(CommandUtils.buildArgs(args, defArgs));
	}

}