package synapticloop.newznab.api.response.model.attributes;

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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import synapticloop.newznab.api.response.BaseModel;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAttributes.class);

	private static final String YES = "yes";
	@JsonProperty("username") private String username;
	@JsonProperty("grabs") private int numGrabs;
	@JsonProperty("role") private String role;
	@JsonProperty("apirequests") private int numApiRequests;
	@JsonProperty("downloadrequests") private int numDownloadRequests;
	private boolean hasMovieView;
	private boolean hasMusicView;
	private boolean hasConsoleView;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("createddate") private Date createdDate;

	/**
	 * Set the movie view flag - this is for internal JSON de-serialisation which 
	 * checks to see if the passed in string is "yes"
	 * 
	 * @param value the check
	 */

	@JsonSetter
	private void setMovieview(String value) {
		this.hasMovieView = YES.equals(value);
	}

	/**
	 * Set the music view flag - this is for internal JSON de-serialisation which 
	 * checks to see if the passed in string is "yes"
	 * 
	 * @param value the check
	 */
	@JsonSetter
	private void setMusicview(String value) {
		this.hasMusicView = YES.equals(value);
	}

	/**
	 * Set the console view flag - this is for internal JSON de-serialisation which 
	 * checks to see if the passed in string is "yes"
	 * 
	 * @param value the check
	 */
	@JsonSetter
	private void setConsoleview(String value) {
		this.hasConsoleView = YES.equals(value);
	}

	/**
	 * Return the username for this account
	 * 
	 * @return the username for this account
	 */
	public String getUsername() { return username; }

	/**
	 * Return the number of grabs that the user may perform within any 24 hour 
	 * period
	 * 
	 * @return the number of grabs that the user may perform
	 */
	public int getNumGrabs() { return numGrabs; }

	/**
	 * Return the name of the role that this user is
	 * 
	 * @return the name of the role that this user is
	 */
	public String getRole() { return role; }

	/**
	 * Return the number of api requests that this user may perform in any one
	 * day
	 * 
	 * @return the number of API requests that the user may perform
	 */
	public int getNumApiRequests() { return numApiRequests; }

	/**
	 * Return the number of download requests that this user may perform in any one
	 * day
	 * 
	 * @return the number of download requests that the user may perform
	 */
	public int getNumDownloadRequests() { return numDownloadRequests; }

	/**
	 * Return whether the user has a movie view
	 * 
	 * @return whether the user has a movie view
	 */
	public boolean getHasMovieView() { return hasMovieView; }

	/**
	 * Return whether the user has a music view
	 * 
	 * @return whether the user has a music view
	 */
	public boolean getHasMusicView() { return hasMusicView; }

	/**
	 * Return whether the user has a console view
	 * 
	 * @return whether the user has a console view
	 */
	public boolean getHasConsoleView() { return hasConsoleView; }

	/**
	 * Return the date at which this user was created
	 * 
	 * @return the date at which this user was created
	 */
	public Date getCreatedDate() { return createdDate; }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
