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

/**
 * The feed response encapsulates data that is returned by the feed API requests.
 * In effect this is an RSS feed with helper methods to drill down into the 
 * structure
 * 
 */
public class FeedResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedResponse.class);

	@JsonProperty("@attributes") private FeedAttribute feedAttribute;

	@JsonProperty("rss")  private RSS rss;

	/**
	 * Get the ID of the feed
	 * 
	 * @return the ID of the feed
	 */
	public long getId() { return(feedAttribute.getId()); }

	/**
	 * Get the feed version
	 * 
	 * @return the feed version
	 */
	public Float getVersion() { return(rss.getVersion()); }

	/**
	 * Get the description of the feed
	 * 
	 * @return the description of the feed
	 */
	public String getDescription() { return(rss.getFeedChannel().getDescription()); }

	/**
	 * Get the branding image for the feed
	 * 
	 * @return the branding image of the feed
	 */
	public Image getImage() { return(rss.getFeedChannel().getImage()); }

	/**
	 * Get the language that this feed is in
	 * 
	 * @return the language that this feed is in
	 */
	public String getLanguage() { return(rss.getFeedChannel().getLanguage()); }

	/**
	 * Get the URL to the homepage
	 * 
	 * @return the URL to the homepage
	 */
	public String getSiteLink() { return(rss.getFeedChannel().getSiteLink()); }

	/**
	 * Get the title of the feed
	 * 
	 * @return the title of the feed
	 */
	public String getTitle() { return(rss.getFeedChannel().getTitle()); }

	/**
	 * Get the web master for this feed
	 * 
	 * @return the web master for this feed
	 */
	public String getWebMaster() { return(rss.getFeedChannel().getWebMaster()); }

	/**
	 * Get the list of items in the feed
	 * 
	 * @return the list of items in the feed
	 */
	public List<FeedItem> getFeedItems() { return(rss.getFeedChannel().getFeedItems()); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
