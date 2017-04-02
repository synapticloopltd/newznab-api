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

public class Image extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Image.class);

	@JsonProperty("url")  private String url;
	@JsonProperty("title")  private String title;
	@JsonProperty("link")  private String link;
	@JsonProperty("description")  private String description;

	public String getUrl() { return url; }
	public String getTitle() { return title; }
	public String getLink() { return link; }
	public String getDescription() { return description; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
