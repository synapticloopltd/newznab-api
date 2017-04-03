package synapticloop.newznab.api.response.model;

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

import synapticloop.newznab.api.response.BaseModel;

public class Searching extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Searching.class);

	@JsonProperty("search")  private Search search;
	@JsonProperty("tv-search")  private Search tvSearch;
	@JsonProperty("movie-search")  private Search movieSearch;
	@JsonProperty("audio-search")  private Search audioSearch;
	@JsonProperty("book-search")  private Search bookSearch;

	/**
	 * Return whether this service has a search functionality available.
	 * 
	 * @return whether there is a search function for this service
	 */
	public boolean getHasSearch() { return(hasSearch(search)); }

	/**
	 * Return whether tv search is enabled for this service
	 * 
	 * @return whether tv search is enabled for this service
	 */
	public boolean getHasTvSearch() { return(hasSearch(tvSearch)); }

	/**
	 * Return whether movie search is enabled for this service
	 * 
	 * @return whether movie search is enabled for this service
	 */
	public boolean getHasMovieSearch() { return(hasSearch(movieSearch)); }

	/**
	 * Return whether audio search is enabled for this service
	 * 
	 * @return whether audio service is enabled for this service
	 */
	public boolean getHasAudioSearch() { return(hasSearch(audioSearch)); }

	/**
	 * Return whether book search is enabled for this service
	 * 
	 * @return whether book search is enabled for this service
	 */
	public boolean getHasBookSearch() { return(hasSearch(bookSearch)); }

	/**
	 * Determine whether the search is not null and is available on the indexer
	 * 
	 * @param search the search object to test
	 * 
	 * @return whether the search is available on this indexer
	 */
	private boolean hasSearch(Search search) {
		return(null != search && search.getSearchAttributes().getIsAvailable());
	}

	@Override
	public Logger getLogger() { return(LOGGER); }
}
