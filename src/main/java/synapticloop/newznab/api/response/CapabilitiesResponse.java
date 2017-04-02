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

import synapticloop.newznab.api.response.model.Categories;
import synapticloop.newznab.api.response.model.Genre;
import synapticloop.newznab.api.response.model.Genres;
import synapticloop.newznab.api.response.model.Group;
import synapticloop.newznab.api.response.model.Groups;
import synapticloop.newznab.api.response.model.Limit;
import synapticloop.newznab.api.response.model.Registration;
import synapticloop.newznab.api.response.model.Searching;
import synapticloop.newznab.api.response.model.Server;

public class CapabilitiesResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CapabilitiesResponse.class);

	@JsonProperty("server") private Server server;
	@JsonProperty("limits") private Limit limit;
	@JsonProperty("registration") private Registration registration;
	@JsonProperty("searching") private Searching searching;
	@JsonProperty("categories") private Categories categories;
	@JsonProperty("groups") private Groups groups;
	@JsonProperty("genres") private Genres genres;

	public String getAppVersion() { return server.getServerAttributes().getAppVersion(); }
	public Float getVersion() { return server.getServerAttributes().getVersion(); }
	public String getTitle() { return server.getServerAttributes().getTitle(); }
	public String getStrapline() { return server.getServerAttributes().getStrapline(); }
	public String getEmail() { return server.getServerAttributes().getEmail(); }
	public String getUrl() { return server.getServerAttributes().getUrl(); }
	public String getImage() { return server.getServerAttributes().getImage(); }

	public int getDefaultResultsLimit() { return(limit.getLimitAttributes().getDefaultResultsLimit()); }
	public int getMaxResultsLimit() { return(limit.getLimitAttributes().getMaxLimit()); }

	public boolean getIsRegistrationAvailable() { return(registration.getRegistrationAttributes().getIsAvailable()); }
	public boolean getIsRegistrationOpen() { return(registration.getRegistrationAttributes().getIsOpen()); }

	public boolean getHasAudioSearch() { return(searching.getHasAudioSearch()); }
	public boolean getHasBookSearch() { return(searching.getHasBookSearch()); }
	public boolean getHasMovieSearch() { return(searching.getHasMovieSearch()); }
	public boolean getHasTvSearch() { return(searching.getHasTvSearch()); }

	public List<Group> getGroups() { return(groups.getGroups()); }
	public List<Genre> getGenres() { return(genres.getGenres()); }

	@Override
	public Logger getLogger() {
		return(LOGGER);
	}
}
