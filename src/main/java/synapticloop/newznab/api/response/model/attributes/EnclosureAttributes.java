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

public class EnclosureAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnclosureAttributes.class);

	@JsonProperty("url")     private String url;
	@JsonProperty("length")  private Long length;
	@JsonProperty("type")    private String type;


	/**
	 * Return the URL of the enclosure which points to the resource
	 * 
	 * @return the URL that points to the resource
	 */
	public String getUrl() { return url; }

	/**
	 * The length of the content that the content of the URL has 
	 * 
	 * @return the length of the content of the URL
	 */
	public Long getLength() { return length; }
	
	/**
	 * Return the type (generally a mime-type) that the enclosure points to
	 * 
	 * @return the type of contents that the enclosure URL is
	 */
	public String getType() { return type; }


	@Override
	public Logger getLogger() { return(LOGGER); }

}
