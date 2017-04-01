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

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CapabilitiesResponse;
import synapticloop.newznab.api.response.RegistrationResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.Category;

public class NewzNabApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(NewzNabApi.class);

	private static final String KEY_REQUEST_PARAMETER_APIKEY = "apikey";
	private static final String KEY_REQUEST_PARAMETER_FUNCTION = "t";
	private static final String KEY_REQUEST_PARAMETER_OUTPUT = "o";
	private static final String KEY_REQUEST_PARAMETER_SEARCH = "q";
	private static final String KEY_REQUEST_PARAMETER_OFFSET = "offset";
	private static final String KEY_REQUEST_PARAMETER_LIMIT = "limit";
	private static final String KEY_REQUEST_PARAMETER_CATEGORIES = "cat";
	private static final String KEY_REQUEST_PARAMETER_GROUPS = "group";
	private static final String KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES = "extended";
	private static final String KEY_REQUEST_PARAMETER_DELETE_FROM_CART = "del";
	private static final String KEY_REQUEST_PARAMETER_MAX_AGE = "maxage";
	private static final String KEY_REQUEST_PARAMETER_RAGE_ID = "rid";
	private static final String KEY_REQUEST_PARAMETER_IMDB_ID = "imdbid";
	private static final String KEY_REQUEST_PARAMETER_GENRE = "genre";
	private static final String KEY_REQUEST_PARAMETER_EMAIL = "email";
	private static final String KEY_REQUEST_PARAMETER_SEASON = "season";
	private static final String KEY_REQUEST_PARAMETER_EPISODE = "episode";

	private static final String VALUE_REQUEST_PARAMETER_CAPS = "caps";
	private static final String VALUE_REQUEST_PARAMETER_JSON = "json";
	private static final String VALUE_REQUEST_PARAMETER_REGISTER = "register";
	private static final String VALUE_REQUEST_PARAMETER_SEARCH = "search";
	private static final String VALUE_REQUEST_PARAMETER_TRUE = "1";


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
		this(HttpClients.createDefault(), apiUrl, apiKey);
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
	public CapabilitiesResponse capabilities() throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_CAPS);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		CloseableHttpResponse httpResponse = executeGet(parameters);
		return(parseResponse(httpResponse, CapabilitiesResponse.class));

	}

	/**
	 * Register a new user with the service
	 * 
	 * @param emailAddress The email address that this user is registered to
	 * 
	 * @return the registration response - or throw an exception if not available
	 * 
	 * @throws IOException If there was a communications error with the API call
	 * @throws NewzNabApiException If there was an error with the API call
	 */
	public RegistrationResponse getRegistration(String emailAddress) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_REGISTER);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);
		parameters.put(KEY_REQUEST_PARAMETER_EMAIL, emailAddress);

		CloseableHttpResponse httpResponse = executeGet(parameters);
		return(new RegistrationResponse(EntityUtils.toString(httpResponse.getEntity())));
	}

	/**
	 * Search for a specific term over all categories
	 * 
	 * @param query the query to search for
	 * 
	 * @return The search response
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public SearchResponse search(String query) throws IOException, NewzNabApiException {
		return(search(query, 0));
	}

	/**
	 * Search for a specific query over all categories, starting at the specified
	 * offset.
	 * 
	 * @param query the query string to search for 
	 * @param offset the offset to start the results from
	 * 
	 * @return The search results
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public SearchResponse search(String query, long offset) throws NewzNabApiException, IOException {
		return(search(query, offset, -1, -1, false, false, new int[0], new String[0]));
	}

	/**
	 * Search for NZBs with the specified query term, with additional filters on 
	 * the query results.
	 * 
	 * @param query the query string to search for
	 * @param offset the offset to start the result set at
	 * @param limit the number of items to return (default 100)
	 * @param maxAgeDays the maximum age of the posting
	 * @param deleteFromCart whether to delete from the cart once it has been downloaded
	 * @param returnExtendedAttributes whether to return extended attributes with the items
	 * @param categories the list of categories to search within {@link Category}
	 * @param groups the list of groups to search within
	 * 
	 * @return The search results
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public SearchResponse search(String query, long offset, int limit, int maxAgeDays, 
			boolean deleteFromCart, 
			boolean returnExtendedAttributes, 
			int[] categories,
			String[] groups) throws NewzNabApiException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		parameters.put(KEY_REQUEST_PARAMETER_SEARCH, query);

		addLongParameter(parameters, KEY_REQUEST_PARAMETER_OFFSET, offset);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_LIMIT, limit);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_MAX_AGE, maxAgeDays);

		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_DELETE_FROM_CART, deleteFromCart);
		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES, returnExtendedAttributes);

		addIntegerArrayParameter(parameters, KEY_REQUEST_PARAMETER_CATEGORIES, categories);
		addStringArrayParameter(parameters, KEY_REQUEST_PARAMETER_GROUPS, groups);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeGet(parameters);
		return(parseResponse(httpResponse, SearchResponse.class));
	}

	public SearchResponse searchTv(String query, int season, int episode) throws NewzNabApiException, IOException {
		return(searchTv(query, season, episode, 0, -1, -1, -1, false, false));
	}

	public SearchResponse searchTv(String query, int season, int episode, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchTv(query, season, episode, offset, limit, -1, -1, false, false));
	}

	public SearchResponse searchTv(String query, int season, int episode, long offset, 
			int limit, 
			int maxAgeDays, 
			int tvRageId,
			boolean deleteFromCart, 
			boolean returnExtendedAttributes) throws NewzNabApiException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		parameters.put(KEY_REQUEST_PARAMETER_SEARCH, query);

		addLongParameter(parameters, KEY_REQUEST_PARAMETER_OFFSET, offset);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_LIMIT, limit);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_MAX_AGE, maxAgeDays);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_RAGE_ID, tvRageId);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_SEASON, season);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_EPISODE, episode);

		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_DELETE_FROM_CART, deleteFromCart);
		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES, returnExtendedAttributes);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeGet(parameters);
		return(parseResponse(httpResponse, SearchResponse.class));
	}

	public SearchResponse searchMovie(String query) throws NewzNabApiException, IOException {
		return(searchMovie(query, null, -1, -1, -1, -1, false, false));
	}

	public SearchResponse searchMovie(String query, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchMovie(query, null, offset, limit, -1, -1, false, false));
	}

	public SearchResponse searchMovie(String query, String genre) throws NewzNabApiException, IOException {
		return(searchMovie(query, genre, -1, -1, -1, -1, false, false));
	}

	public SearchResponse searchMovie(String query, String genre, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchMovie(query, genre, offset, limit, -1, -1, false, false));
	}

	public SearchResponse searchMovie(String query, String genre, long offset, 
			int limit, 
			int maxAgeDays, 
			int imdbId,
			boolean deleteFromCart, 
			boolean returnExtendedAttributes) throws NewzNabApiException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		parameters.put(KEY_REQUEST_PARAMETER_SEARCH, query);

		addLongParameter(parameters, KEY_REQUEST_PARAMETER_OFFSET, offset);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_LIMIT, limit);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_MAX_AGE, maxAgeDays);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_IMDB_ID, imdbId);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_GENRE, genre);

		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_DELETE_FROM_CART, deleteFromCart);
		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES, returnExtendedAttributes);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeGet(parameters);
		return(parseResponse(httpResponse, SearchResponse.class));
	}

	private void addIntegerArrayParameter(Map<String, String> parameters, String key, int[] values) {
		if(values.length != 0) {
			StringBuilder stringBuilder = new StringBuilder();
			// now go through the categories
			for (Object i : values) {
				if(stringBuilder.length() > 0) {
					stringBuilder.append(",");
				}
				stringBuilder.append(i);
			}
			parameters.put(key, stringBuilder.toString());
		}
		
	}

	private void addStringArrayParameter(Map<String, String> parameters, String key, String[] values) {
		if(values.length != 0) {
			StringBuilder stringBuilder = new StringBuilder();
			// now go through the categories
			for (String i : values) {
				if(stringBuilder.length() > 0) {
					stringBuilder.append(",");
				}
				stringBuilder.append(i);
			}
			parameters.put(key, stringBuilder.toString());
		}
		
	}

	private void addIntegerParameter(Map<String, String> parameters, String key, int value) {
		if(value >= 0) {
			parameters.put(key, Integer.toString(value));
		}
	}

	private void addLongParameter(Map<String, String> parameters, String key, long value) {
		if(value >= 0) {
			parameters.put(key, Long.toString(value));
		}
	}

	private void addStringParameter(Map<String, String> parameters, String key, String value) {
		if(null != value) {
			parameters.put(KEY_REQUEST_PARAMETER_APIKEY, apiKey);
		}
	}

	private void addBooleanParameter(Map<String, String> parameters, String key, boolean value) {
		if(value) {
			parameters.put(key, VALUE_REQUEST_PARAMETER_TRUE);
		}
	}

	private CloseableHttpResponse executeGet(Map<String, String> parameters) throws IOException, NewzNabApiException {
		URI uri = this.buildUri(parameters);

		HttpGet httpGet = new HttpGet(uri);

		CloseableHttpResponse httpResponse = this.execute(httpGet);

		// you will either get an OK or a partial content
		switch(httpResponse.getStatusLine().getStatusCode()) {
		case HttpStatus.SC_OK:
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


	private <T> T parseResponse(HttpResponse response, Class<T> entityClass) throws NewzNabApiException {
		try {
			return parseJson(response.getEntity(), entityClass);
		} catch (IOException ex) {
			throw new NewzNabApiException(ex);
		}
	}

	private ObjectMapper initializeObjectMapperJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
		return mapper;
	}

	private <T> T parseJson(HttpEntity responseEntity, Class<T> type) throws IOException {
		String encoding = responseEntity.getContentEncoding() != null ? responseEntity.getContentEncoding().getValue() : "UTF-8";
		String jsonString = IOUtils.toString(responseEntity.getContent(), encoding);
		try {
			return initializeObjectMapperJson().readValue(jsonString, type);
		} catch (Exception ex) {
			LOGGER.error(String.format("%s", jsonString));
			throw ex;
		}
	}
}
