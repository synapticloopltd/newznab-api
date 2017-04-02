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

	public String getAppVersion() { return appVersion; }
	public Float getVersion() { return version; }
	public String getTitle() { return title; }
	public String getStrapline() { return strapline; }
	public String getEmail() { return email; }
	public String getUrl() { return url; }
	public String getImage() { return image; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
