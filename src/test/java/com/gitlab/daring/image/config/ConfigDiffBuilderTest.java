package com.gitlab.daring.image.config;

import com.typesafe.config.Config;
import org.junit.Test;
import static com.gitlab.daring.image.config.ConfigUtils.configFromString;
import static com.gitlab.daring.image.config.ConfigUtils.emptyConfig;
import static org.junit.Assert.assertEquals;

public class ConfigDiffBuilderTest {

	@Test
	public void testBuild() {
		ConfigDiffBuilder b = new ConfigDiffBuilder();
		Config ec = emptyConfig();
		Config c1 = configFromString("p1 = v1, p2 = { p3 = v3}");
		assertEquals(c1, b.build(c1, ec));
		assertEquals(ec, b.build(c1, c1));
		Config c2 = configFromString("p1 = cv1, p2 = { p3 = v3}");
		assertEquals(configFromString("p1 = v1"), b.build(c1, c2));
		Config c3 = configFromString("p1 = v1, p2 = { p3 = cv3}");
		assertEquals(configFromString("p2.p3 = v3"), b.build(c1, c3));
		Config c4 = configFromString("p1 = v1, p2 = { p3 = v3}, p4 = v4");
		assertEquals(ec, b.build(c1, c4));
	}

}