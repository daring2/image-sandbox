package com.gitlab.daring.sandbox.image.util;

import org.junit.Test;

import java.awt.*;

import static com.gitlab.daring.sandbox.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.sandbox.image.util.GeometryUtils.scale;
import static org.junit.Assert.assertEquals;

public class GeometryUtilsTest {

	@Test
	public void testScale() {
		Dimension d1 = new Dimension(40, 20);
		assertEquals(d1, scale(d1, 1));
		assertEquals(new Dimension(60, 30), scale(d1, 1.5));
		assertEquals(new Dimension(10, 5), scale(d1, 0.25));
		assertEquals(new Dimension(4, 2), scale(d1, 0.1));
	}

	@Test
	public void testCenterRect() {
		Dimension d1 = new Dimension(40, 20);
		assertEquals(new Rectangle(0, 0, 40, 20), getCenterRect(d1, 1));
		assertEquals(new Rectangle(15, 8, 10, 5), getCenterRect(d1, 0.25));
	}

}