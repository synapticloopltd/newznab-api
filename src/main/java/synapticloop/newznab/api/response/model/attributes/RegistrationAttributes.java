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
import com.fasterxml.jackson.annotation.JsonSetter;

import synapticloop.newznab.api.response.BaseModel;

public class RegistrationAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationAttributes.class);

	private static final String YES = "yes";

	@JsonProperty("available")  private boolean isAvailable;
	@JsonProperty("open")  private boolean isOpen;

	@JsonSetter
	private void setAvailable(String value) {
		this.isAvailable = YES.equals(value);
	}

	@JsonSetter
	private void setOpen(String value) {
		this.isOpen = YES.equals(value);
	}

	public boolean getIsAvailable() { return isAvailable; }
	public boolean getIsOpen() { return isOpen; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
