package synapticloop.newznab.api.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
 * Copyright (c) 2016 Synapticloop.
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.bean.Category;
import synapticloop.newznab.api.response.bean.Genre;
import synapticloop.newznab.api.response.bean.Group;

public class CapabilitiesResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CapabilitiesResponse.class);

	@Override
	public Logger getLogger() {
		return(LOGGER);
	}
}
