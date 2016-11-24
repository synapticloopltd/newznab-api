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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CapsResponse;
import synapticloop.newznab.api.response.RegistrationResponse;

@PowerMockIgnore("javax.net.ssl.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(EntityUtils.class)
public class NewzNabApiTest {
	private NewzNabApi newzNabApi;
	@Mock private CloseableHttpClient mockCloseableHttpClient = null; 
	@Mock private CloseableHttpResponse mockCloseableHttpResponse = null; 
	@Mock private HttpEntity mockHttpEntity = null; 
	@Mock private InputStream mockInputStream = null;
	@Mock private StatusLine mockStatusLine = null;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		CloseableHttpClient httpClient = HttpUtils.getDefaultClient();
		initMocks(this);

		newzNabApi = new NewzNabApi(httpClient, "http://lolo.sickbeard.com/api");
	}

	@Test
	public void testGetCaps() throws IOException, NewzNabApiException {
		CapsResponse capsResponse = newzNabApi.getCaps();
		assertNotNull(capsResponse);
	}

	@Test(expected = NewzNabApiException.class)
	public void testGetRegistrationClosed() throws IOException, NewzNabApiException {
		newzNabApi.getRegistration("anon" + System.currentTimeMillis() + "@example.com");
	}

	@Test
	public void testGoodRegistration() throws NewzNabApiException, ClientProtocolException, IOException {
		PowerMockito.mockStatic(EntityUtils.class);
		when(mockStatusLine.getStatusCode()).thenReturn(200);
		when(mockCloseableHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
		when(EntityUtils.toString(mockHttpEntity)).thenReturn("<?xml version=\"1.0\" encoding=\"UTF-8\"?><register username=\"uf8dff84\" password=\"cd20a675\" apikey=\"75a605dfc47c0edc8f4637d5677844e1\"/>");
		when(mockCloseableHttpResponse.getEntity()).thenReturn(mockHttpEntity);
		when(mockCloseableHttpClient.execute(any())).thenReturn(mockCloseableHttpResponse);
		NewzNabApi newzNabApi = new NewzNabApi(mockCloseableHttpClient, "http://example.com/api");

		RegistrationResponse registrationResponse = newzNabApi.getRegistration("anon@example.com");
		assertEquals("uf8dff84", registrationResponse.getUsername());
		assertEquals("cd20a675", registrationResponse.getPassword());
		assertEquals("75a605dfc47c0edc8f4637d5677844e1", registrationResponse.getApikey());
	}
	
}
