package com.gitlab.daring.image.util;

import org.junit.Test;

import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.*;
import static org.junit.Assert.assertEquals;

public class GeometryUtilsTest {

	@Test
	public void testCenterRect() {
		Rectangle r1 = new Rectangle(10, 5, 40, 20);
		assertEquals(r1, getCenterRect(r1, 1));
		assertEquals(new Rectangle(25, 13, 10, 5), getCenterRect(r1, 0.25));
	}

	@Test
	public void testScale() {
		Dimension d1 = new Dimension(40, 20);
		assertEquals(d1, scale(d1, 1));
		assertEquals(new Dimension(60, 30), scale(d1, 1.5));
		assertEquals(new Dimension(10, 5), scale(d1, 0.25));
		assertEquals(new Dimension(4, 2), scale(d1, 0.1));
	}

	@Test
	public void testScaleToMax() {
		Dimension d1 = new Dimension(20, 10);
		assertEquals(d1, scaleToMax(d1, d1));
		assertEquals(d1, scaleToMax(d1, new Dimension(40, 20)));
		assertEquals(new Dimension(10, 5), scaleToMax(d1, new Dimension(10, 20)));
		assertEquals(new Dimension(10, 5), scaleToMax(d1, new Dimension(40, 5)));
	}

}