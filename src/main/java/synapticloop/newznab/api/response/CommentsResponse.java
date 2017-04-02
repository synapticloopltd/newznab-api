package synapticloop.newznab.api.response;

import java.util.List;

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

import synapticloop.newznab.api.response.model.CommentChannel;
import synapticloop.newznab.api.response.model.CommentItem;
import synapticloop.newznab.api.response.model.Image;
import synapticloop.newznab.api.response.model.attributes.Attribute;

public class CommentsResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentsResponse.class);

	@JsonProperty("@attributes") private Attribute attribute;
	@JsonProperty("channel") private CommentChannel commentChannel;

	public Float getVersion() { return(attribute.getVersion()); }
	public String getDescription() { return(commentChannel.getDescription()); }
	public Image getImage() { return(commentChannel.getImage()); }
	public String getLanguage() { return(commentChannel.getLanguage()); }
	public String getLink() { return(commentChannel.getLink()); }
	public String getTitle() { return(commentChannel.getTitle()); }
	public String getWebMaster() { return(commentChannel.getWebMaster()); }
	public List<CommentItem> getItems() { return(commentChannel.getItems()); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
