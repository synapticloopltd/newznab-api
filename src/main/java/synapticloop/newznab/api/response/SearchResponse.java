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

import synapticloop.newznab.api.response.model.VersionAttribute;
import synapticloop.newznab.api.response.model.Channel;
import synapticloop.newznab.api.response.model.Image;
import synapticloop.newznab.api.response.model.Item;

/**
 * The search response encapsulates the data returned from the search API calls
 */
public class SearchResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchResponse.class);

	@JsonProperty("@attributes")  private VersionAttribute attribute;
	@JsonProperty("channel")  private Channel channel;

	/**
	 * Get the version of the indexer for this API
	 * 
	 * @return the version of the indexer for this API
	 */
	public Float getVersion() { return(attribute.getVersion()); }

	/**
	 * Get the title for the indexer
	 * 
	 * @return the title for the indexer
	 */
	public String getTitle() { return(channel.getTitle()); }

	/**
	 * Get the description of the indexer
	 * 
	 * @return the description of the indexer
	 */
	public String getDescription() { return(channel.getDescription()); }

	/**
	 * Get the URL of the homepage of the indexer
	 * 
	 * @return the URL of the homepage of the indexer
	 */
	public String getLink() { return(channel.getLink()); }

	/**
	 * Get the language of the indexer
	 * 
	 * @return the language of the indexer
	 */
	public String getLanguage() { return(channel.getLanguage()); }

	/**
	 * Get the web master that looks after this indexer
	 * 
	 * @return the web master that looks after this indexer
	 */
	public String getWebMaster() { return(channel.getWebMaster()); }

	/**
	 * Get the list of items that is returned by the search
	 * 
	 * @return the list of items that is returned by the search
	 */
	public List<Item> getItems() { return(channel.getItems()); }

	/**
	 * Get the branding image for this indexer
	 * 
	 * @return the branding image for this indexer
	 */
	public Image getImage() { return(channel.getImage()); }

	/**
	 * Get the starting offset of the search results
	 * 
	 * @return the starting offset of the search results
	 */
	public Long getOffset() { return(channel.getResponse().getResponseAttributes().getOffset()); }

	/**
	 * Get the total number of results for this search
	 * 
	 * @return the total number of results for this search
	 */
	public Long getTotal() { return(channel.getResponse().getResponseAttributes().getTotal()); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
