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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;
import synapticloop.newznab.api.response.model.attributes.EnclosureAttributes;
import synapticloop.newznab.api.response.model.attributes.ItemAttributes;

public class FeedItem extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedItem.class);

	private static final String KEY_USENETDATE = "usenetdate";
	private static final String KEY_COMMENTS = "comments";
	private static final String KEY_FILES = "files";
	private static final String KEY_GRABS = "grabs";
	private static final String KEY_GROUP = "group";
	private static final String KEY_POSTER = "poster";
	private static final String KEY_PASSWORD = "password";

	private static final String DEFAULT_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss Z";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_FORMAT);


	@JsonProperty("title")         private String title;
	@JsonProperty("guid")          private Guid guid;
	@JsonProperty("link")          private String nzbLink;
	@JsonProperty("comments")      private String commentsLink;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_FORMAT)
	@JsonProperty("pubDate")       private Date publishDate;

	@JsonProperty("category")      private String categoryName;
	@JsonProperty("description")   private String description;
	@JsonProperty("enclosure")     private EnclosureAttributes enclosureAttributes;
	@JsonProperty("newznab:attr")  private List<ItemAttributes> itemAttributes = new ArrayList<ItemAttributes>();

	/**
	 * Get the title of the feed item
	 * 
	 * @return the title of the feed item
	 */
	public String getTitle() { return title; }

	/**
	 * Get the link to the NZB
	 * 
	 * @return the link to the NZB
	 */
	public String getNzbLink() { return nzbLink; }

	/**
	 * Get the link to the comments
	 * 
	 * @return the link to the comments
	 */
	public String getCommentsLink() { return commentsLink; }

	/**
	 * Get the date that this feed item was published
	 * 
	 * @return the date that this feed was published
	 */
	public Date getPublishDate() { return publishDate; }

	/**
	 * Get the name of the category that this feed item was indexed in
	 * 
	 * @return the name of the category that this feed item was indexed in
	 */
	public String getCategoryName() { return categoryName; }

	/**
	 * Get the description of the feed item
	 * 
	 * @return the description of the feed item
	 */
	public String getDescription() { return description; }

	/**
	 * Get the list of the item's attributes
	 * 
	 * @return the list of the item's attributed
	 */
	public List<ItemAttributes> getItemAttributes() { return itemAttributes; }

	/**
	 * Get the poster of the feed item
	 * 
	 * @return the poster of the feed item
	 */
	public String getPoster() { return(getItemAttribute(KEY_POSTER)); }

	/**
	 * Get the group that this feed item was posted to
	 * 
	 * @return the group that this feed item was posted to
	 */
	public String getGroup() { return(getItemAttribute(KEY_GROUP)); }

	/**
	 * Get the number of grabs that this item has had
	 * 
	 * @return the number of grabs that this item has had
	 */
	public int getNumGrabs() { 
		String itemAttribute = getItemAttribute(KEY_GRABS);
		if(null != itemAttribute) {
			try {
				return(Integer.parseInt(itemAttribute));
			} catch(NumberFormatException ex) {
				LOGGER.error("Could not parse number of grabs for value '{}'", itemAttribute);
			}
		}
		return 0; 
	}

	/**
	 * Get the number of files that make up this release
	 * 
	 * @return the number of files that make up this release
	 */
	public int getNumFiles() { 
		String itemAttribute = getItemAttribute(KEY_FILES);
		if(null != itemAttribute) {
			try {
				return(Integer.parseInt(itemAttribute));
			} catch(NumberFormatException ex) {
				LOGGER.error("Could not parse number of files for value '{}'", itemAttribute);
			}
		}
		return 0; 
	}

	/**
	 * Get the number of comments that have been made on this item
	 * 
	 * @return the number of comments that have been made on this item
	 */
	public int getNumComments() { 
		String itemAttribute = getItemAttribute(KEY_COMMENTS);
		if(null != itemAttribute) {
			try {
				return(Integer.parseInt(itemAttribute));
			} catch(NumberFormatException ex) {
				LOGGER.error("Could not parse number of comments for value '{}'", itemAttribute);
			}
		}
		return 0; 
	}

	/**
	 * Get the GUID for this feed item
	 * 
	 * @return the GUID for this feed item
	 */
	public Guid getGuid() { return(guid); }

	/**
	 * Get the date that this item was posted
	 * 
	 * @return the date that this item was posted
	 */
	public Date getUsenetDate() {
		String itemAttribute = getItemAttribute(KEY_USENETDATE);
		if(null != itemAttribute) {
			try {
				return(SIMPLE_DATE_FORMAT.parse(itemAttribute));
			} catch(ParseException ex) {
				LOGGER.error("Could not parse usenet date with format '{}' for String '{}'", DEFAULT_DATE_FORMAT, itemAttribute);
			}
		}
		return null;
	}

	/**
	 * Return whether this has been marked as having a password
	 * 
	 * @return whether this has been marked as having a passwod
	 */
	public boolean getHasPassword() {
		return("0".equals(getItemAttribute(KEY_PASSWORD)));
	}

	/**
	 * Get the item attribute with the associated name
	 * 
	 * @param name the key to look up
	 * 
	 * @return the item's attribute with the associated name
	 */
	public String getItemAttribute(String name) {
		if(null == name) {
			return(null);
		}

		for (ItemAttributes itemAttributes : itemAttributes) {
			if(name.equals(itemAttributes.getName())) {
				return(itemAttributes.getValue());
			}
		}
		return(null);
	}

	@Override
	public Logger getLogger() { return(LOGGER); }
}
