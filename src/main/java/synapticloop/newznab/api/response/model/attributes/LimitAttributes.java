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

public class LimitAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(LimitAttributes.class);

	@JsonProperty("max")      private int maxLimit;
	@JsonProperty("default")  private int defaultLimit;

	/**
	 * Get the maximum number of results that can be returned from a search or 
	 * a feed
	 * 
	 * @return the maximum number of results that can be returned from a search 
	 *   or a feed
	 */
	public int getMaxLimit() { return maxLimit; }

	/**
	 * Get the default number of results that are returned from a search or a 
	 *   feed
	 * 
	 * @return default number of results that are returned from a search or a 
	 *   feed
	 */
	public int  getDefaultResultsLimit() { return defaultLimit; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
