package synapticloop.newznab.api.response;

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

import synapticloop.newznab.api.response.model.attributes.UserAttributes;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserResponse.class);

	@JsonProperty("@attributes") private UserAttributes userAttributes;

	/**
	 * Return the username for this account
	 * 
	 * @return the username for this account
	 */
	public String getUsername() { return userAttributes.getUsername(); }

	/**
	 * Return the number of grabs that the user may perform within any 24 hour 
	 * period
	 * 
	 * @return the number of grabs that the user may perform
	 */
	public int getNumGrabs() { return userAttributes.getNumGrabs(); }

	/**
	 * Return the name of the role that this user is
	 * 
	 * @return the name of the role that this user is
	 */
	public String getRole() { return userAttributes.getRole(); }

	/**
	 * Return the number of api requests that this user may perform in any one
	 * day
	 * 
	 * @return the number of API requests that the user may perform
	 */
	public int getNumApiRequests() { return userAttributes.getNumApiRequests(); }

	/**
	 * Return the number of download requests that this user may perform in any one
	 * day
	 * 
	 * @return the number of download requests that the user may perform
	 */
	public int getNumDownloadRequests() { return userAttributes.getNumDownloadRequests(); }

	/**
	 * Return whether the user has a movie view
	 * 
	 * @return whether the user has a movie view
	 */
	public boolean getHasMovieView() { return userAttributes.getHasMovieView(); }

	/**
	 * Return whether the user has a music view
	 * 
	 * @return whether the user has a music view
	 */
	public boolean getHasMusicView() { return userAttributes.getHasMusicView(); }

	/**
	 * Return whether the user has a console view
	 * 
	 * @return whether the user has a console view
	 */
	public boolean getHasConsoleView() { return userAttributes.getHasConsoleView(); }

	/**
	 * Return the date at which this user was created
	 * 
	 * @return the date at which this user was created
	 */
	public Date getCreatedDate() { return userAttributes.getCreatedDate(); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
