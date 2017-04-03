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

import static synapticloop.newznab.api.Category.*;
import static synapticloop.newznab.api.RequestConstants.*;

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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CapabilitiesResponse;
import synapticloop.newznab.api.response.CartResponse;
import synapticloop.newznab.api.response.CommentResponse;
import synapticloop.newznab.api.response.CommentsResponse;
import synapticloop.newznab.api.response.DetailsResponse;
import synapticloop.newznab.api.response.FeedResponse;
import synapticloop.newznab.api.response.RegistrationResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.UserResponse;

/**
 * This class provides requests to the indexer's API 
 */
public class NewzNabApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(NewzNabApi.class);

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
	 * Instantiate the NewzNab API with an endpoint URL and an API key
	 * 
	 * @param apiUrl The URL to connect to
	 * @param apiKey The api Key for access
	 */
	public NewzNabApi(String apiUrl, String apiKey) {
		this(HttpClients.createDefault(), apiUrl, apiKey);
	}

	/**
	 * Instantiate the NewzNab API with an endpoint URL and a Http client
	 * 
	 * @param client the client to use
	 * @param apiUrl the api URL to hit
	 */
	public NewzNabApi(CloseableHttpClient client, String apiUrl) {
		this(client, apiUrl, null);
	}

	/**
	 * Instantiate the NewzNab API with an endpoint URL and a Http client and an
	 * api key
	 * 
	 * @param client the client to use
	 * @param apiUrl the api URL to hit
	 * @param apiKey the api key to use
	 */
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
	public CapabilitiesResponse getCapabilities() throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_CAPS);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		CapabilitiesResponse parseResponse = parseResponse(httpResponse, CapabilitiesResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	/**
	 * Register a new user with the service.  This will return a response with a
	 * username, password and api key, which can then be used to perform other 
	 * operations.
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
		RegistrationResponse parseResponse = parseResponse(httpResponse, RegistrationResponse.class, true);
		httpResponse.close();
		return parseResponse;
	}

	/**
	 * Search for a specific term over all categories.
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
	 * Search for a specific query over the specified categories, starting at the specified
	 * offset.
	 * 
	 * @param query the query string to search for 
	 * @param offset the offset to start the results from
	 * @param categories the array of categories to search within
	 * @return The search results
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public SearchResponse search(String query, long offset, int[] categories) throws NewzNabApiException, IOException {
		return(search(query, offset, -1, -1, false, false, categories, new String[0]));
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
		SearchResponse parseResponse = parseResponse(httpResponse, SearchResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	/**
	 * Search TV shows
	 * 
	 * @param query the term to query 
	 * 
	 * @return The search results
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public SearchResponse searchTv(String query) throws NewzNabApiException, IOException {
		return(searchTv(query, -1, -1, 0, -1, -1, -1, -1, -1, false, false));
	}

	/**
	 * Search TV shows
	 * 
	 * @param query the term to query 
	 * @param season the season number
	 * @param episode the episode number
	 * 
	 * @return The search results
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public SearchResponse searchTv(String query, int season, int episode) throws NewzNabApiException, IOException {
		return(searchTv(query, season, episode, 0, -1, -1, -1, -1, -1, false, false));
	}

	/**
	 * Search tv shows
	 * 
	 * @param query the term to query 
	 * @param season the season number
	 * @param episode the episode number
	 * @param offset for paginated results, the offset number
	 * @param limit the maximum number of results to return
	 * 
	 * @return The search results
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public SearchResponse searchTv(String query, int season, int episode, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchTv(query, season, episode, offset, limit, -1, -1, -1, -1, false, false));
	}

	/**
	 * Search TV shows, if any of the parameters are null (or -1 for integers) 
	 * then they will not be used within the query 
	 * 
	 * @param query the term to query 
	 * @param season the season number
	 * @param episode the episode number
	 * @param offset for paginated results, the offset number
	 * @param limit the maximum number of results to return
	 * @param maxAgeDays the maximum number of days to go back in the search
	 * @param rageId the rage ID
	 * @param tvdbId the TVDB id
	 * @param tvMazeId the TV Maze ID
	 * @param deleteFromCart whether to delete this result from the cart (if it exists in the cart)
	 * @param returnExtendedAttributes whether to return extended attributes
	 * 
	 * @return The search results
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
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
		SearchResponse parseResponse = parseResponse(httpResponse, SearchResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	public SearchResponse searchMovie(String query) throws NewzNabApiException, IOException {
		return(searchMovie(query, null, -1, -1, -1, null, false, false));
	}

	public SearchResponse searchMovie(String query, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchMovie(query, null, offset, limit, -1, null, false, false));
	}

	public SearchResponse searchMovie(String query, String genre) throws NewzNabApiException, IOException {
		return(searchMovie(query, genre, -1, -1, -1, null, false, false));
	}

	public SearchResponse searchMovie(String query, String genre, long offset, int limit) throws NewzNabApiException, IOException {
		return(searchMovie(query, genre, offset, limit, -1, null, false, false));
	}

	public SearchResponse searchMovie(String query, String genre, long offset, 
			int limit, 
			int maxAgeDays, 
			String imdbId,
			boolean deleteFromCart, 
			boolean returnExtendedAttributes) throws NewzNabApiException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_SEARCH_MOVIE);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		parameters.put(KEY_REQUEST_PARAMETER_SEARCH, query);

		addLongParameter(parameters, KEY_REQUEST_PARAMETER_OFFSET, offset);

		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_LIMIT, limit);
		addIntegerParameter(parameters, KEY_REQUEST_PARAMETER_MAX_AGE, maxAgeDays);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_IMDB_ID, imdbId);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_GENRE, genre);

		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_DELETE_FROM_CART, deleteFromCart);
		addBooleanParameter(parameters, KEY_REQUEST_PARAMETER_EXTENDED_ATTRIBUTES, returnExtendedAttributes);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		SearchResponse parseResponse = parseResponse(httpResponse, SearchResponse.class);
		httpResponse.close();
		return parseResponse;
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
		SearchResponse parseResponse = parseResponse(httpResponse, SearchResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	public SearchResponse searchBook(String query) throws NewzNabApiException, IOException {
		return(searchBook(query, -1, -1, null, null, -1, false, false));
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
		SearchResponse parseResponse = parseResponse(httpResponse, SearchResponse.class);
		httpResponse.close();
		return parseResponse;
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
	public DetailsResponse getDetails(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_DETAILS);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);


		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		DetailsResponse parseResponse = parseResponse(httpResponse, DetailsResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	/**
	 * Get the .nfo from the release identified by the GUID (if it exists)
	 * 
	 * @param guid the GUID for the release
	 * 
	 * @return the NFO contents as text
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public String getNfo(String guid) throws IOException, NewzNabApiException {
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
		CartResponse parseResponse = parseResponse(httpResponse, CartResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	public CartResponse cartDelete(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_CART_DELETE);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		CartResponse parseResponse = parseResponse(httpResponse, CartResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	/**
	 * Get the feed for the items that in the cart
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForCart() throws IOException, NewzNabApiException {
		return(getFeedForCategory(VALUE_REQUEST_PARAMETER_FEED_CART));
	}

	/**
	 * Get the latest indexed releases for the site
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForSite() throws IOException, NewzNabApiException {
		return(getFeedForCategory(VALUE_REQUEST_PARAMETER_FEED_SITE));
	}

	/**
	 * Get the feed for all of the tv shows that are marked as 'my shows'
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForMyShows() throws IOException, NewzNabApiException {
		return(getFeedForCategory(VALUE_REQUEST_PARAMETER_FEED_MY_SHOWS));
	}

	/**
	 * Get the feed for all of the movies that are marked as 'my movies'
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForMyMovies() throws IOException, NewzNabApiException {
		return(getFeedForCategory(VALUE_REQUEST_PARAMETER_FEED_MY_MOVIES));
	}

	/**
	 * Get the latest indexed movie releases (if unavailable, this will return an 
	 * error or an empty list of items in the FeedResponse).
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForMovies() throws IOException, NewzNabApiException {
		return(getFeedForCategory(CATEGORY_MOVIES));
	}

	/**
	 * Get the latest indexed console releases (if unavailable, this will return an 
	 * error or an empty list of items in the FeedResponse).
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForConsoles() throws IOException, NewzNabApiException {
		return(getFeedForCategory(CATEGORY_CONSOLE));
	}

	/**
	 * Get the latest indexed audio releases (if unavailable, this will return an 
	 * error or an empty list of items in the FeedResponse).
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForAudio() throws IOException, NewzNabApiException {
		return(getFeedForCategory(CATEGORY_AUDIO));
	}

	/**
	 * Get the latest indexed pc releases (if unavailable, this will return an 
	 * error or an empty list of items in the FeedResponse).
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForPc() throws IOException, NewzNabApiException {
		return(getFeedForCategory(CATEGORY_PC));
	}

	/**
	 * Get the latest indexed tv releases (if unavailable, this will return an 
	 * error or an empty list of items in the FeedResponse).
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForTv() throws IOException, NewzNabApiException {
		return(getFeedForCategory(CATEGORY_TV));
	}

	/**
	 * Get the latest indexed triple-x releases (if unavailable, this will return an 
	 * error or an empty list of items in the FeedResponse).
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForXXX() throws IOException, NewzNabApiException {
		return(getFeedForCategory(CATEGORY_XXX));
	}

	/**
	 * Get the latest indexed 'other' releases (if unavailable, this will return an 
	 * error or an empty list of items in the FeedResponse).
	 * 
	 * @return The list of items in the feed
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForOther() throws IOException, NewzNabApiException {
		return(getFeedForCategory(CATEGORY_OTHER));
	}

	/**
	 * Return the feed for the specified category
	 * 
	 * @param category the category to get the feed for {@link Category}
	 * 
	 * @return the feed for the category
	 * 
	 * @throws IOException if there was an error communicating with the API
	 * @throws NewzNabApiException if there was an error with the API
	 */
	public FeedResponse getFeedForCategory(int category) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_RSS_R, apiKey);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_RSS_I, VALUE_REQUEST_PARAMETER_TRUE);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_RSS_DL, VALUE_REQUEST_PARAMETER_TRUE);
		// we need to put this in manually as addIntegerParameter will not allow negative numbers      
		parameters.put(KEY_REQUEST_PARAMETER_RSS_T, Integer.toString(category));

		CloseableHttpResponse httpResponse = executeRssGet(parameters);
		FeedResponse parseResponse = parseResponse(httpResponse, FeedResponse.class, true);
		httpResponse.close();
		return parseResponse;
	}

	public UserResponse getUser(String username) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_USER);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_USERNAME, username);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		UserResponse parseResponse = parseResponse(httpResponse, UserResponse.class);
		httpResponse.close();
		return parseResponse;
	}

	public CommentsResponse getComments(String guid) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_COMMENTS);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		CommentsResponse parseResponse = parseResponse(httpResponse, CommentsResponse.class);
		httpResponse.close();
		return parseResponse;

	}

	public CommentResponse addComment(String guid, String comment) throws IOException, NewzNabApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY_REQUEST_PARAMETER_FUNCTION, VALUE_REQUEST_PARAMETER_COMMENT_ADD);
		parameters.put(KEY_REQUEST_PARAMETER_OUTPUT, VALUE_REQUEST_PARAMETER_JSON);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_ID, guid);
		addStringParameter(parameters, KEY_REQUEST_PARAMETER_TEXT, comment);

		addStringParameter(parameters, KEY_REQUEST_PARAMETER_APIKEY, apiKey);

		CloseableHttpResponse httpResponse = executeApiGet(parameters);
		CommentResponse parseResponse = parseResponse(httpResponse, CommentResponse.class);
		httpResponse.close();
		return parseResponse;
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
