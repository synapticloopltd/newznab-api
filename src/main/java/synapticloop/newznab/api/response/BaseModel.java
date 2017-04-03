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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The BaseModel catches any properties that are on the JSON to be de-serialised 
 * and do not have any setter defined for them.  This will log a warning to the 
 * output to be picked up and mapped in future versions.
 */
public abstract class BaseModel {
	@JsonIgnore protected Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * Get the logger to use for any messages that need to be output.
	 * 
	 * @return the logger to use for any messages to be output
	 */
	public abstract Logger getLogger();

	/**
	 * Set additional properties that were found on the JSON string, but were 
	 * not mapped to a field.  This will output a warning message in the logs so 
	 * that additional properties can be set on the object
	 * 
	 * @param name The name of the property to set
	 * @param value the value of the object to set.
	 */
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		getLogger().warn("No native setter for key '{}' with value '{}'", name, value);
		this.additionalProperties.put(name, value);
	}

	/**
	 * Return an additional property which was not picked up by the JSON de-serialisation 
	 * process
	 * 
	 * @param name the name of the property to retrieve
	 * 
	 * @return the property value
	 */
	public Object getAdditionalProperty(String name) {
		return(this.additionalProperties.get(name));
	}
}
