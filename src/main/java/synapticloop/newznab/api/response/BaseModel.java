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

public abstract class BaseModel {
	@JsonIgnore protected Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public abstract Logger getLogger();

	/**
	 * Set additional properties that were found on the JSON string, but were 
	 * not mapped to a field.  This will output a warning message in the logs
	 * 
	 * @param name The name of the property to set
	 * @param value the value of the object to set.
	 */
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		getLogger().warn("No native setter for key '{}' with value '{}'", name, value);
		this.additionalProperties.put(name, value);
	}

}
