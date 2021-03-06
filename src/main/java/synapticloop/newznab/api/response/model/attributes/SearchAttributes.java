package synapticloop.newznab.api.response.model.attributes;

import java.util.ArrayList;

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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import synapticloop.newznab.api.response.BaseModel;

public class SearchAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAttributes.class);

	private static final String YES = "yes";

	@JsonProperty("available")        private boolean isAvailable;
	@JsonProperty("supportedParams")  private List<String> supportedParams = new ArrayList<String>();


	/**
	 * Set the available flag - this is for internal JSON de-serialisation which 
	 * checks to see if the passed in string is "yes"
	 * 
	 * @param value the check
	 */
	@JsonSetter
	private void setAvailable(String value) {
		this.isAvailable = YES.equals(value);
	}

	/**
	 * Return whether this type of search is available
	 * 
	 * @return whether this type of search is available
	 */
	public boolean getIsAvailable() { return isAvailable; }
	
	/**
	 * Return the list of supported parameters that can be queried against.
	 * 
	 * @return the list of supported parameters that can be queried against
	 */
	public List<String> getSupportedParams() { return supportedParams; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
