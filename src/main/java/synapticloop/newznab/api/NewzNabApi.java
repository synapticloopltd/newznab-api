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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CapsResponse;
import synapticloop.newznab.api.response.RegistrationResponse;

public class NewzNabApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(NewzNabApi.class);

	private static final String KEY_REQUEST_PARAMETER_APIKEY = "apikey";
	private static final String KEY_REQUEST_PARAMETER_FUNCTION = "t";
	private static final String KEY_REQUEST_PARAMETER_OUTPUT = "o";
	private static final String KEY_REQUEST_PARAMETER_EMAIL = "email";

	private static final String VALUE_REQUEST_PARAMETER_CAPS = "caps";
	private static final String VALUE_REQUEST_PARAMETER_JSON = "json";
	private static final String VALUE_REQUEST_PARAMETER_REGISTER = "register";

	private final CloseableHttpClient client;

	private final String apiUrl;
	private final String apiKey;


	/**
	 * Instantiate the NewzNab API
	 * 
	 * @param apiUrl the API endpoint URL to connect to 
	 */
	public NewzNabApi(String apiUrl) {
		this(apiUrl, null);
	}

	public NewzNabApi(String apiUrl, String apiKey) {
		this(HttpClients.createDefault(), apiUrl, null);
	}

	public NewzNabApi(CloseableHttpClient client, String apiUrl) {
		this(client, apiUrl, null);
	}

	public NewzNabApi(CloseableHttpClient client, String apiUrl, String apiKey) {
		this.client = client;
		this.apiUrl = apiUrl;
		this.apiKey = apiKey;
	}

	/**
	 * Get the capabilities that the NewzNab API contains
	 * 
	 * @return the capabilities response
	 * 
	 * @throws IOException If there was an error connecting with the API
	 * @throws NewzNabApiException if there was an error with the call
	 */
	public CapsResponse getCaps() throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_CAPS);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		CloseableHttpResponse httpResponse = executeGet(parameters);
		return(new CapsResponse(EntityUtils.toString(httpResponse.getEntity())));
	}

	public RegistrationResponse getRegistration(String emailAddress) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_REGISTER);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);
		parameters.put(KEY_REQUEST_PARAMETER_EMAIL, emailAddress);

		CloseableHttpResponse httpResponse = executeGet(parameters);
		return(new RegistrationResponse(EntityUtils.toString(httpResponse.getEntity())));
	}

	private CloseableHttpResponse executeGet(Map<String, String> parameters) throws IOException, NewzNabApiException {
		URI uri = this.buildUri(parameters);

		HttpGet httpGet = new HttpGet(uri);

		CloseableHttpResponse httpResponse = this.execute(httpGet);

		// you will either get an OK or a partial content
		switch(httpResponse.getStatusLine().getStatusCode()) {
		case HttpStatus.SC_OK:
		case HttpStatus.SC_PARTIAL_CONTENT:
			return httpResponse;
		}

		final NewzNabApiException failure = new NewzNabApiException(
				EntityUtils.toString(httpResponse.getEntity()), 
				new HttpResponseException(
						httpResponse.getStatusLine().getStatusCode(), 
						httpResponse.getStatusLine().getReasonPhrase()));

		throw failure;
	}

	private CloseableHttpResponse execute(final HttpUriRequest request) throws IOException, NewzNabApiException {
		LOGGER.debug("{} request to URL '{}'", request.getMethod(), request.getURI());
		final CloseableHttpResponse httpResponse = client.execute(request);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received status code of: {}, for {} request to url '{}'", httpResponse.getStatusLine().getStatusCode(), request.getMethod(), request.getURI());
		}
		return httpResponse;
	}

	/**
	 * Return the URI for this request, which adds any parameters found in the
	 * 'parameters' data structure
	 *
	 * @return The URI for this request, with properly encoded parameters
	 *
	 * @throws IOException If there was an error building the URI
	 */
	private URI buildUri(Map<String, String> parameters) throws IOException {
		try {
			URIBuilder uriBuilder = new URIBuilder(apiUrl);

			for (final String key : parameters.keySet()) {
				uriBuilder.addParameter(key, parameters.get(key));
			}

			return uriBuilder.build();
		} catch(URISyntaxException e) {
			throw new IOException(e);
		}
	}
}
