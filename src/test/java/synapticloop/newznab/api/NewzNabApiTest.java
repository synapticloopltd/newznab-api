package synapticloop.newznab.api;

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

import java.io.IOException;

import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import synapticloop.newznab.api.exception.NewzNabApiException;


public class NewzNabApiTest {
	private NewzNabApi newzNabApi;

	@Before
	public void setup() {
		newzNabApi = new NewzNabApi(HttpClients.createDefault(), "http://lolo.sickbeard.com/api");
	}

	@Test
	public void testGetCaps() throws IOException, NewzNabApiException {
		newzNabApi.getCaps();
		assertTrue(true);
	}

	@Test
	public void testGetRegistration() throws IOException, NewzNabApiException {
		newzNabApi.getRegistration();
		assertTrue(true);
	}


}
