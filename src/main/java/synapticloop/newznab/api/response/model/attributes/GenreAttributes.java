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

public class GenreAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenreAttributes.class);

	@JsonProperty("id")          private int id;
	@JsonProperty("name")        private String name;
	@JsonProperty("categoryid")  private int category;

	
	/**
	 * Get the ID of the Genre
	 * 
	 * @return The ID of the genre
	 */
	public int getId() { return id; }

	/**
	 * Get the readable name of the genre
	 * 
	 * @return the readable name of the genre
	 */
	public String getName() { return name; }

	/**
	 * Get the category number that this genre applies to
	 * 
	 * @return the category that this genre applies to
	 */
	public int getCategory() { return category; }


	@Override
	public Logger getLogger() { return(LOGGER); }

}
