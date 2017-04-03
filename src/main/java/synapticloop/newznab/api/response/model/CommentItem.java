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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class CommentItem extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentItem.class);

	private static final String DEFAULT_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss Z";


	@JsonProperty("title")         private String title;
	@JsonProperty("guid")          private String guidLink;
	@JsonProperty("link")          private String commentLink;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_FORMAT)
	@JsonProperty("pubDate")       private Date publishDate;

	@JsonProperty("description")   private String comment;

	/**
	 * Get the title of the comment
	 * 
	 * @return the title of the comment
	 */
	public String getTitle() { return title; }

	/**
	 * Get the link to the comment
	 * 
	 * @return the link to the comment
	 */
	public String getCommentLink() { return commentLink; }

	/**
	 * Get the date that the comment was published
	 * 
	 * @return the date that the comment was published
	 */
	public Date getPublishDate() { return publishDate; }

	/**
	 * Get the comment
	 * 
	 * @return the comment
	 */
	public String getComment() { return comment; }

	/**
	 * Get the GUID link
	 * 
	 * @return the GUID link
	 */
	public String getGuidLink() { return(guidLink); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
