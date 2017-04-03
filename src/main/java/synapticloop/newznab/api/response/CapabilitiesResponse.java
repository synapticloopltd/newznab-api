package synapticloop.newznab.api.response;

import java.util.List;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.response.model.Categories;
import synapticloop.newznab.api.response.model.Genre;
import synapticloop.newznab.api.response.model.Genres;
import synapticloop.newznab.api.response.model.Group;
import synapticloop.newznab.api.response.model.Groups;
import synapticloop.newznab.api.response.model.Limit;
import synapticloop.newznab.api.response.model.Registration;
import synapticloop.newznab.api.response.model.Searching;
import synapticloop.newznab.api.response.model.Server;

/**
 * This class encapsulates the data returned from the capabilities request from 
 * the API
 */
public class CapabilitiesResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CapabilitiesResponse.class);

	@JsonProperty("server") private Server server;
	@JsonProperty("limits") private Limit limit;
	@JsonProperty("registration") private Registration registration;
	@JsonProperty("searching") private Searching searching;
	@JsonProperty("categories") private Categories categories;
	@JsonProperty("groups") private Groups groups;
	@JsonProperty("genres") private Genres genres;

	/**
	 * Get the application version of the usenet indexer
	 * 
	 * @return the application version of the usenet indexer
	 */
	public String getAppVersion() { return server.getServerAttributes().getAppVersion(); }

	/**
	 * Get the version of the NewzNab Indexer application
	 * 
	 * @return the version of the NewzNab Indexer application
	 */
	public Float getVersion() { return server.getServerAttributes().getVersion(); }

	/**
	 * Get the title for the usenet indexer
	 * 
	 * @return the title for the usenet indexer
	 */
	public String getTitle() { return server.getServerAttributes().getTitle(); }

	/**
	 * Get the strapline for the usenet indexer
	 * 
	 * @return the strapline for the usenet indexer
	 */
	public String getStrapline() { return server.getServerAttributes().getStrapline(); }

	/**
	 * Get the email address for the owner of the usenet indexer
	 * 
	 * @return the email address for the owner of the usenet indexer
	 */
	public String getEmail() { return server.getServerAttributes().getEmail(); }

	/**
	 * Get the URL for the site 
	 * 
	 * @return the URL for the site
	 */
	public String getUrl() { return server.getServerAttributes().getUrl(); }

	/**
	 * Get the branding image for the usenet indexer
	 * 
	 * @return the branding image for the usenet indexer
	 */
	public String getImage() { return server.getServerAttributes().getImage(); }

	/**
	 * Get the default number of results returned for a feed or a search
	 * 
	 * @return the default number of results returned for a feed or a search
	 */
	public int getDefaultResultsLimit() { return(limit.getLimitAttributes().getDefaultResultsLimit()); }

	/**
	 * Get the maximum number of results that will be allowed to be returned for 
	 * a feed or a search
	 * 
	 * @return the maximum number of results that will be allowed to be returned 
	 *   for a feed or search
	 */
	public int getMaxResultsLimit() { return(limit.getLimitAttributes().getMaxLimit()); }

	/**
	 * Get whether registration is available on this indexer
	 * 
	 * @return whether registration is available on this indexer
	 */
	public boolean getIsRegistrationAvailable() { return(registration.getRegistrationAttributes().getIsAvailable()); }

	/**
	 * Get whether registration is open on this indexer
	 * 
	 * @return whether registration is open on this indexer
	 */
	public boolean getIsRegistrationOpen() { return(registration.getRegistrationAttributes().getIsOpen()); }

	/**
	 * Get whether this indexer has audio search functionality {@link NewzNabApi#searchMusic(String)}
	 * 
	 * @return whether this indexer has audio search functionality 
	 */
	public boolean getHasAudioSearch() { return(searching.getHasAudioSearch()); }

	/**
	 * Get whether this indexer has book search functionality {@link NewzNabApi#searchBook(String)}
	 * 
	 * @return whether this indexer has book search functionality 
	 */
	public boolean getHasBookSearch() { return(searching.getHasBookSearch()); }

	/**
	 * Get whether this indexer has movie search functionality {@link NewzNabApi#searchMovie(String)}
	 * 
	 * @return whether this indexer has movie search functionality 
	 */
	public boolean getHasMovieSearch() { return(searching.getHasMovieSearch()); }

	/**
	 * Get whether this indexer has TV search functionality {@link NewzNabApi#searchTv(String)}
	 * 
	 * @return whether this indexer has TV search functionality 
	 */
	public boolean getHasTvSearch() { return(searching.getHasTvSearch()); }

	/**
	 * Get the groups that are being indexed by this server
	 * 
	 * @return the groups that are being indexed by this server
	 */
	public List<Group> getGroups() { return(groups.getGroups()); }

	/**
	 * Get the genres that are defined by this indexer, the genres also define 
	 * which categories they are available on.
	 * 
	 * @return the genres that are defined by this indexer
	 */
	public List<Genre> getGenres() { return(genres.getGenres()); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
