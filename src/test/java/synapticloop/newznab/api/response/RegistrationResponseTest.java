package synapticloop.newznab.api.response;

/*
 * Copyright (c) 2016 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.newznab.api.exception.NewzNabApiException;

public class RegistrationResponseTest {
	
	@Before
	public void setup() {
	}
	
	@Test
	public void testRegistrationBean() throws NewzNabApiException {
		RegistrationResponse registrationResponse = new RegistrationResponse("\n" + 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<register username=\"uf8dff84\" password=\"cd20a675\" apikey=\"75a605dfc47c0edc8f4637d5677844e1\"/>\n");
		assertEquals("uf8dff84", registrationResponse.getUsername());
		assertEquals("cd20a675", registrationResponse.getPassword());
		assertEquals("75a605dfc47c0edc8f4637d5677844e1", registrationResponse.getApikey());
	}
	
}
