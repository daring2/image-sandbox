package com.gitlab.daring.image.util;

import org.junit.Test;

import java.util.function.Consumer;

import static com.gitlab.daring.image.util.CommonUtils.tryRun;
import static org.junit.Assert.assertEquals;

public class CommonUtilsTest {

	@Test
	public void testTryRun() {
		Exception[] errRef = new Exception[1];
		Consumer<Exception> errFunc = e -> errRef[0] = e;
		Exception e1 = new Exception("e1");
		tryRun(() -> { throw e1; }, errFunc);
		assertEquals(e1, errRef[0]);
	}

}