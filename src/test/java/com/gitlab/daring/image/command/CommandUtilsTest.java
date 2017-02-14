package com.gitlab.daring.image.command;

import org.junit.Test;

import java.util.List;

import static com.gitlab.daring.image.command.CommandUtils.splitScript;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class CommandUtilsTest {

	@Test
	public void testSplitScript() {
		assertEquals(emptyList(), splitScript("").toList());
		assertEquals(asList("c1", "c2", "c3"), splitScript("c1; c2\n c3").toList());
		assertEquals(asList("c1", "c3", "c6"), splitScript("c1; -c2; c3\n// c4, c5\nc6").toList());
	}

	@Test
	public void testParseArgs() {
		assertEquals(emptyList(), parseArgs(""));
		assertEquals(asList("p1", "p2", "p3"), parseArgs("p1, p2, p3"));
		assertEquals(asList("d1", "d2", "d3"), parseArgs("", "d1", "d2", "d3"));
		assertEquals(asList("p1", "d2", "d3"), parseArgs("p1", "d1", "d2", "d3"));
	}

	List<String> parseArgs(String argStr, String... defArgs) {
		return asList(CommandUtils.parseArgs(argStr, asList(defArgs)));
	}

	@Test
	public void testBuildArgs() {
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