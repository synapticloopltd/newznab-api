package synapticloop.newznab.api;

/*
 * Copyright (c) 2016-2017 Synapticloop.
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
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CapabilitiesResponse;
import synapticloop.newznab.api.response.CartResponse;
import synapticloop.newznab.api.response.FeedResponse;
import synapticloop.newznab.api.response.DetailsResponse;
import synapticloop.newznab.api.response.RegistrationResponse;
import synapticloop.newznab.api.response.SearchResponse;

public class NewzNabApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(NewzNabApi.class);

	private static final String KEY_REQUEST_PARAMETER_APIKEY = "apikey";
	private static final String KEY_REQUEST_PARAMETER_ID = "id";
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
	private static final String KEY_REQUEST_PARAMETER_TVDB_ID = "tvdbid";
	private static final String KEY_REQUEST_PARAMETER_TV_MAZE_ID = "tvmazeid";
	private static final String KEY_REQUEST_PARAMETER_IMDB_ID = "imdbid";
	private static final String KEY_REQUEST_PARAMETER_GENRE = "genre";
	private static final String KEY_REQUEST_PARAMETER_GENRES = "genre";
	private static final String KEY_REQUEST_PARAMETER_EMAIL = "email";
	private static final String KEY_REQUEST_PARAMETER_SEASON = "season";
	private static final String KEY_REQUEST_PARAMETER_EPISODE = "episode";
	private static final String KEY_REQUEST_PARAMETER_ALBUM = "album";
	private static final String KEY_REQUEST_PARAMETER_ARTIST = "artist";
	private static final String KEY_REQUEST_PARAMETER_LABEL = "label";
	private static final String KEY_REQUEST_PARAMETER_TRACK = "track";
	private static final String KEY_REQUEST_PARAMETER_TITLE = "title";
	private static final String KEY_REQUEST_PARAMETER_RAW = "raw";
	private static final String KEY_REQUEST_PARAMETER_AUTHOR = "author";

	private static final String VALUE_REQUEST_PARAMETER_CAPS = "caps";
	private static final String VALUE_REQUEST_PARAMETER_GET = "get";
	private static final String VALUE_REQUEST_PARAMETER_JSON = "json";
	private static final String VALUE_REQUEST_PARAMETER_GETNFO = "getnfo";
	private static final String VALUE_REQUEST_PARAMETER_REGISTER = "register";
	private static final String VALUE_REQUEST_PARAMETER_SEARCH = "search";
	private static final String VALUE_REQUEST_PARAMETER_SEARCH_TV = "tvsearch";
	private static final String VALUE_REQUEST_PARAMETER_SEARCH_MOVIE = "movie";
	private static final String VALUE_REQUEST_PARAMETER_SEARCH_MUSIC = "music";
	private static final String VALUE_REQUEST_PARAMETER_SEARCH_BOOK = "book";
	private static final String VALUE_REQUEST_PARAMETER_CART_ADD = "cartadd";
	private static final String VALUE_REQUEST_PARAMETER_CART_DELETE = "cartdel";
	private static final String VALUE_REQUEST_PARAMETER_DETAILS = "details";
	private static final String VALUE_REQUEST_PARAMETER_TRUE = "1";


	private final CloseableHttpClient client;

	private final String apiUrl;
	private final String rssUrl;
	private final String apiKey;


	/**
	 * Instantiate the NewzNab API without any credentials
	 * 
	 * @param apiUrl the API endpoint URL to connect to 
	 */
	public NewzNabApi(String apiUrl) {
		this(apiUrl, null);
	}

	/**
	 * Instantiate the NewzNab API with an enpoint URL and an API key
	 * 
	 * @param apiUrl The URL to connect to
	 * @param apiKey The api Key for access
	 */
	public NewzNabApi(String apiUrl, String apiKey) {
		this(HttpClients.createDefault(), apiUrl, apiKey);
	}

	public NewzNabApi(CloseableHttpClient client, String apiUrl) {
		this(client, apiUrl, null);
	}

	public NewzNabApi(CloseableHttpClient client, String apiUrl, String apiKey) {
		this.client = client;
		this.apiUrl = apiUrl;
		this.rssUrl = apiUrl.replace("/api", "/rss");
		this.apiKey = apiKey;
	}

	/**
	 * Get the capabilities that the NewzNab API can respond to
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

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
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
	public RegistrationResponse register(String emailAddress) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_REGISTER);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);
		parameters.put(KEY_REQUEST_PARAMETER_EMAIL, emailAddress);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, RegistrationResponse.class, true));
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

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, SearchResponse.class));
	}

	public SearchResponse searchTv(String query, int season, int episode) throws NewzNabApiException, IOException {
		return(searchTv(query, season, episode, 0, -1, -1, -1, -1, -1, false, false));
	}

	public SearchResponse searchTv(String query, int season, int episode, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchTv(query, season, episode, offset, limit, -1, -1, -1, -1, false, false));
	}

	public SearchResponse searchTv(String query, int season, int episode, long offset, 
			int limit, 
			int maxAgeDays, 
			int rageId,
			int tvdbId,
			int tvMazeId,
			boolean deleteFromCart, 
			boolean returnExtendedAttributes) throws NewzNabApiException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH_TV);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		parameters.put(KEY_REQUEST_PARAMETER_SEARCH, query);

		addLongParameter(parameters, KEY_REQUEST_PARAMETER_OFFSET, offset);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_LIMIT, limit);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_MAX_AGE, maxAgeDays);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_SEASON, season);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_EPISODE, episode);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_RAGE_ID, rageId);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_TVDB_ID, tvdbId);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_TV_MAZE_ID, tvMazeId);

		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_DELETE_FROM_CART, deleteFromCart);
		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES, returnExtendedAttributes);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
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
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH_MOVIE);
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

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, SearchResponse.class));
	}

	public SearchResponse searchMusic(String query) throws NewzNabApiException, IOException {
		return(searchMusic(query, -1, -1, null, null, null, null, -1, null, null, -1, false, false));
	}

	public SearchResponse searchMusic(String query, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchMusic(query, offset, limit, null, null, null, null, -1, null, null, -1, false, false));
	}

	public SearchResponse searchMusic(String query, long offset, int limit, 
			String album,
			String artist,
			String label,
			String track,
			int year,
			String[] genres,
			int[] categories,
			int maxAgeDays, 
			boolean deleteFromCart, 
			boolean returnExtendedAttributes) throws NewzNabApiException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH_MUSIC);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		parameters.put(KEY_REQUEST_PARAMETER_SEARCH, query);

		addLongParameter(parameters, KEY_REQUEST_PARAMETER_OFFSET, offset);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_LIMIT, limit);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ALBUM, album);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ARTIST, artist);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_LABEL, label);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_TRACK, track);


		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_MAX_AGE, maxAgeDays);
		addStringArrayParameter(parameters, KEY_REQUEST_PARAMETER_GENRES, genres);

		addIntegerArrayParameter(parameters, KEY_REQUEST_PARAMETER_CATEGORIES, categories);

		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_DELETE_FROM_CART, deleteFromCart);
		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES, returnExtendedAttributes);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, SearchResponse.class));
	}

	public SearchResponse searchBook(String query, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchBook(query, offset, limit, null, null, -1, false, false));
	}

	public SearchResponse searchBook(String query, long offset, int limit, 
			String title,
			String author,
			int maxAgeDays, 
			boolean deleteFromCart, 
			boolean returnExtendedAttributes) throws NewzNabApiException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH_BOOK);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		parameters.put(KEY_REQUEST_PARAMETER_SEARCH, query);

		addLongParameter(parameters, KEY_REQUEST_PARAMETER_OFFSET, offset);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_LIMIT, limit);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_TITLE, title);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_AUTHOR, author);


		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_MAX_AGE, maxAgeDays);

		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_DELETE_FROM_CART, deleteFromCart);
		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES, returnExtendedAttributes);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, SearchResponse.class));
	}

	/**
	 * Get the details for the specified GUID NZB.  This provides a lot more details 
	 * than the items that are returned in the search results.
	 * 
	 * @param guid The GUID to get the details on
	 * 
	 * @return The Details response which behaves like the Search Results response
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public DetailsResponse details(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_DETAILS);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);


		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, DetailsResponse.class));
	}

	public String nfo(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_GETNFO);
		parameters.put(KEY_REQUEST_PARAMETER_RAW, VALUE_REQUEST_PARAMETER_TRUE);


		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		String responseString = EntityUtils.toString(httpResponse.getEntity());
		// now we know whether there was an error...
		int indexOf = responseString.indexOf("<error code=\"");
		if(indexOf > 0) {
			throw new NewzNabApiException(responseString.substring(indexOf).trim());
		}

		return responseString;
	}

	/**
	 * Get the NZB XML file - which can then be used to be downloaded.
	 * 
	 * @param guid The GUID of the NZB to download
	 * 
	 * @return The NZB XML file as a string representation
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public String getNzb(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_GET);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		String responseString = EntityUtils.toString(httpResponse.getEntity());
		// now we know whether there was an error...
		int indexOf = responseString.indexOf("<error code=\"");
		if(indexOf > 0) {
			throw new NewzNabApiException(responseString.substring(indexOf).trim());
		}

		return responseString;
	}

	public CartResponse cartAdd(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_CART_ADD);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, CartResponse.class));
	}

	public CartResponse cartDelete(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_CART_DELETE);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		return(parseResponse(httpResponse, CartResponse.class));
	}

	public FeedResponse getCartFeed() throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();

		addStringParameter(parameters, "r", apiKey);
		addStringParameter(parameters, "i", "1");
		addStringParameter(parameters, "dl", "1");
		addStringParameter(parameters, "t", "-2");

		CloseableHttpResponse httpResponse = executeRssGet(parameters);
		return(parseResponse(httpResponse, FeedResponse.class, true));
	}

	private void addMapParameters(Map<String, String> parameters, Map<String, String> additionalParameters) {
		if(null == additionalParameters) {
			return;
		}

		for (String key : additionalParameters.keySet()) {
			addStringParameter(parameters, key, additionalParameters.get(key));
		}
	}

	private void addIntegerArrayParameter(Map<String, String> parameters, String key, int[] values) {
		if(null == values) {
			return;
		}

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
		if(null == values) {
			return;
		}

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
			parameters.put(key, value);
		}
	}

	private void addBooleanParameter(Map<String, String> parameters, String key, boolean value) {
		if(value) {
			parameters.put(key, VALUE_REQUEST_PARAMETER_TRUE);
		}
	}

	private CloseableHttpResponse executeApiGet(Map<String, String> parameters) throws IOException, NewzNabApiException {
		return(executeGet(this.apiUrl, parameters));
	}

	private CloseableHttpResponse executeRssGet(Map<String, String> parameters) throws IOException, NewzNabApiException {
		return(executeGet(this.rssUrl, parameters));
	}

	private CloseableHttpResponse executeGet(String url, Map<String, String> parameters) throws IOException, NewzNabApiException {
		URI uri = this.buildUri(url, parameters);

		HttpGet httpGet = new HttpGet(uri);

		LOGGER.debug("{} request to URL '{}'", httpGet.getMethod(), httpGet.getURI());
		final CloseableHttpResponse httpResponse = client.execute(httpGet);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received status code of: {}, for {} request to url '{}'", httpResponse.getStatusLine().getStatusCode(), httpGet.getMethod(), httpGet.getURI());
		}

		// you will either get an OK or a partial content
		switch(httpResponse.getStatusLine().getStatusCode()) {
		case HttpStatus.SC_OK:
			// if they do return 200, as the response __was__ successful, there may 
			// have been an error in the response
			return httpResponse;
		}

		final NewzNabApiException failure = new NewzNabApiException(
				EntityUtils.toString(httpResponse.getEntity()), 
				new HttpResponseException(
						httpResponse.getStatusLine().getStatusCode(), 
						httpResponse.getStatusLine().getReasonPhrase()));

		throw failure;
	}
	/**
	 * Return the URI for this request, which adds any parameters found in the
	 * 'parameters' data structure
	 *
	 * @return The URI for this request, with properly encoded parameters
	 *
	 * @throws IOException If there was an error building the URI
	 */
	private URI buildUri(String url, Map<String, String> parameters) throws IOException {
		try {
			URIBuilder uriBuilder = new URIBuilder(url);

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
			return parseJson(response.getEntity(), entityClass, false);
		} catch (IOException ex) {
			throw new NewzNabApiException(ex);
		}
	}

	private <T> T parseResponse(HttpResponse response, Class<T> entityClass, boolean transformXml) throws NewzNabApiException {
		try {
			return parseJson(response.getEntity(), entityClass, transformXml);
		} catch (IOException ex) {
			throw new NewzNabApiException(ex);
		}
	}

	/**
	 * Initialise the Jackson object mapper
	 * 
	 * @return the initialised object mapper
	 */
	private ObjectMapper initializeObjectMapperJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
		return mapper;
	}

	/**
	 * Parse the JSON response and Jackson parse it, returning the suitable object.
	 * 
	 * @param responseEntity The HTTP response entity which has the content
	 * @param type The type of the marshalled object
	 * 
	 * @return the marshalled object from the JSON
	 * 
	 * @throws IOException If there was an error m=arshalling the response
	 * @throws NewzNabApiException if there was an error in the calls
	 */
	private <T> T parseJson(HttpEntity responseEntity, Class<T> type, boolean parseXml) throws IOException, NewzNabApiException {
		String encoding = responseEntity.getContentEncoding() != null ? responseEntity.getContentEncoding().getValue() : "UTF-8";
		String jsonString = IOUtils.toString(responseEntity.getContent(), encoding);

		// now we know whether there was an error...
		int indexOf = jsonString.indexOf("<error code=\"");
		if(indexOf > 0) {
			throw new NewzNabApiException(jsonString.substring(indexOf).trim());
		}

		if(parseXml) {
			LOGGER.trace("Converting XML string to JSON");
			jsonString = XML.toJSONObject(jsonString).toString();
		}

		LOGGER.trace("Received response of: {}", jsonString);

		try {
			return initializeObjectMapperJson().readValue(jsonString, type);
		} catch (Exception ex) {
			LOGGER.error("Error '{}', object mapping for: {}", ex.getMessage(), jsonString);
			throw ex;
		}
	}
}
