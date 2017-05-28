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

	@Test
	public void initToken_shouldNotTriggerNoClassDefFoundError() throws Exception {
		// this triggers the bug
		Token obj = new Token(new Date(), "jwt", "source", "source-name", "source-version");

		assertNotNull(obj);
	}

	@Test
	public void initLease_shouldNotTriggerNoClassDefFoundError() throws Exception {
		// this triggers the bug
		Lease obj = new Lease("lease-id", "name", new Date(), "mock://url");

		assertNotNull(obj);
	}

}
