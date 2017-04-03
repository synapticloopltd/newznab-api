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

public class DetailsResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(DetailsResponse.class);

	@JsonProperty("@attributes")  private VersionAttribute attribute;
	@JsonProperty("channel")  private Channel channel;

	public Float getVersion() { return(attribute.getVersion()); }
	public String getTitle() { return(channel.getTitle()); }
	public String getDescription() { return(channel.getDescription()); }
	public String getLink() { return(channel.getLink()); }
	public String getLanguage() { return(channel.getLanguage()); }
	public String getWebMaster() { return(channel.getWebMaster()); }
	public List<Item> getItems() { return(channel.getItems()); }
	public Image getImage() { return(channel.getImage()); }

	public Long getOffset() { return(channel.getResponse().getResponseAttributes().getOffset()); }
	public Long getTotal() { return(channel.getResponse().getResponseAttributes().getTotal()); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
