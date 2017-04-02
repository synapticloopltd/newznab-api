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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonIgnoreProperties({"atom:link"})
public class FeedChannel extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedChannel.class);

	@JsonProperty("title")        private String title;
	@JsonProperty("description")  private String description;
	@JsonProperty("link")         private String siteLink;
	@JsonProperty("language")     private String language;
	@JsonProperty("webMaster")    private String webMaster;
	@JsonProperty("item")         private List<FeedItem> items = new ArrayList<FeedItem>();
	@JsonProperty("image")        private Image image;

	// we never get the 'category' object back
	@JsonProperty("category")     private Object category;

	public String getTitle() { return title; }
	public String getDescription() { return description; }
	public String getSiteLink() { return siteLink; }
	public String getLanguage() { return language; }
	public String getWebMaster() { return webMaster; }
	public List<FeedItem> getFeedItems() { return items; }
	public Image getImage() { return image; }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
