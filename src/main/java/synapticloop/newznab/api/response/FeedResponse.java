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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.model.FeedItem;
import synapticloop.newznab.api.response.model.Image;
import synapticloop.newznab.api.response.model.RSS;
import synapticloop.newznab.api.response.model.attributes.FeedAttribute;

public class FeedResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedResponse.class);

	@JsonProperty("@attributes") private FeedAttribute feedAttribute;

	@JsonProperty("rss")  private RSS rss;

	public long getId() { return(feedAttribute.getId()); }
	public Float getVersion() { return(rss.getVersion()); }

	public String getDescription() { return(rss.getFeedChannel().getDescription()); }
	public Image getImage() { return(rss.getFeedChannel().getImage()); }
	public String getLanguage() { return(rss.getFeedChannel().getLanguage()); }
	public String getSiteLink() { return(rss.getFeedChannel().getSiteLink()); }
	public String getTitle() { return(rss.getFeedChannel().getTitle()); }
	public String getWebMaster() { return(rss.getFeedChannel().getWebMaster()); }
	public List<FeedItem> getFeedItems() { return(rss.getFeedChannel().getFeedItems()); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}