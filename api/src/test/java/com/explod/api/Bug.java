package com.explod.api;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class Bug {

	@Test
	public void testsRunCorrectly() {
		// yay!!
	}

	@Test
	public void initUserCreated_shouldNotTriggerNoClassDefFoundError() throws Exception {
		// this triggers the bug
		UserCreated obj = new UserCreated("username", "foo@example.com", new Date());

		assertNotNull(obj);
	}

}
