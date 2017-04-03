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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class ServerAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerAttributes.class);

	@JsonProperty("appversion")  private String appVersion;
	@JsonProperty("version")  private Float version;
	@JsonProperty("title")  private String title;
	@JsonProperty("strapline")  private String strapline;
	@JsonProperty("email")  private String email;
	@JsonProperty("url")  private String url;
	@JsonProperty("image")  private String image;

	/**
	 * Get the application version for this indexer 
	 * 
	 * @return the application version for this indexer
	 */
	public String getAppVersion() { return appVersion; }

	/**
	 * The version of NewzNab that this indexer is running
	 * 
	 * @return the version of NewzNab that this indexer is running
	 */
	public Float getVersion() { return version; }

	/**
	 * Get the title of this indexer
	 * 
	 * @return the title of this indexer
	 */
	public String getTitle() { return title; }

	/**
	 * Get the strapline for this indexer
	 * 
	 * @return the strapline for this indexer
	 */
	public String getStrapline() { return strapline; }

	/**
	 * Get the email address for the owner of this indexer
	 * 
	 * @return the email address for the owner of this indexer
	 */
	public String getEmail() { return email; }

	/**
	 * Get the URL for the homepage for this indexer
	 * 
	 * @return the URL for the homepage of this indexer
	 */
	public String getUrl() { return url; }

	/**
	 * Get the branding image for this indexer
	 * 
	 * @return the branding image for this indexer
	 */
	public String getImage() { return image; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
