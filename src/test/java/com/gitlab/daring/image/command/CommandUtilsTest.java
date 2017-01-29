package com.gitlab.daring.image.command;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class CommandUtilsTest {

	@Test
	public void testParseParams() throws Exception {
		assertEquals(emptyList(), parseParams(""));
		assertEquals(asList("p1", "p2", "p3"), parseParams("p1, p2, p3"));
		assertEquals(asList("d1", "d2", "d3"), parseParams("", "d1", "d2", "d3"));
		assertEquals(asList("p1", "d2", "d3"), parseParams("p1", "d1", "d2", "d3"));
	}

	List<String> parseParams(String paramStr, String... defParams) {
		return asList(CommandUtils.parseParams(paramStr, asList(defParams)));
	}

}