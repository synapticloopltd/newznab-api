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
import synapticloop.newznab.api.response.model.attributes.ItemAttributes;

public class Item extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Item.class);

	private static final String DEFAULT_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss Z";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

	private static final String GUID = "guid";
	private static final String KEY_USENETDATE = "usenetdate";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_COMMENTS = "comments";
	private static final String KEY_FILES = "files";
	private static final String KEY_GRABS = "grabs";
	private static final String KEY_GROUP = "group";
	private static final String KEY_POSTER = "poster";

	@JsonProperty("title")        private String title;
	@JsonProperty("guid")         private String detailsLink;
	@JsonProperty("link")         private String nzbLink;
	@JsonProperty("comments")     private String commentsLink;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_FORMAT)
	@JsonProperty("pubDate")      private Date publishDate;

	@JsonProperty("category")     private String categoryName;
	@JsonProperty("description")  private String description;
	@JsonProperty("enclosure")    private Enclosure enclosure;
	@JsonProperty("attr")         private List<ItemAttribute> itemAttributes = new ArrayList<ItemAttribute>();

	/**
	 * Get the title for the item
	 * 
	 * @return the title for the item
	 */
	public String getTitle() { return title; }

	/**
	 * Get the link to the details for the item
	 * 
	 * @return the link to the details for the item
	 */
	public String getDetailsLink() { return detailsLink; }

	/**
	 * Get the link to download the NZB for this item
	 * 
	 * @return the link to download the NZB for this item
	 */
	public String getNzbLink() { return nzbLink; }

	/**
	 * Get the link to the comments for this item
	 * 
	 * @return the link to the comments for this item
	 */
	public String getCommentsLink() { return commentsLink; }

	/**
	 * Get the date that this item was published
	 * 
	 * @return the date that this item was published
	 */
	public Date getPublishDate() { return publishDate; }

	/**
	 * Get the name of the category that this item belongs in
	 * 
	 * @return the name of the category that this item belongs in
	 */
	public String getCategoryName() { return categoryName; }

	/**
	 * Get the description for this item - the returned String __MAY__ be already 
	 * be formatted in HTML
	 * 
	 * @return the description for this item
	 */
	public String getDescription() { return description; }

	/**
	 * Get the length of the item (in bytes)
	 * 
	 * @return the length of the item (in bytes)
	 */
	public Long getLength() { return(enclosure.getEnclosureAttributes().getLength()); }

	/**
	 * Get the mime type of the item
	 * 
	 * @return the mime type of the item
	 */
	public String getType() { return(enclosure.getEnclosureAttributes().getType()); }

	/**
	 * Get the attributes for the item
	 * 
	 * @return the attributes for the item
	 */
	public List<ItemAttribute> getItemAttributes() { return itemAttributes; }

	/**
	 * Get the name of the poster of this item
	 * 
	 * @return the name of the poster of this item
	 */
	public String getPoster() { return(getItemAttribute(KEY_POSTER)); }

	/**
	 * Get the usenet group that this item was posted to
	 * 
	 * @return the usenet group that this item was posted to
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
	 * Get the number of files that this release contains
	 * 
	 * @return the number of files that this release contains
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
	 * Get the GUID for this item
	 * 
	 * @return the GUID for this item
	 */
	public String getGuid() { return(getItemAttribute(GUID)); }

	/**
	 * Get the date that this item was posted on usenet
	 * 
	 * @return the date that this item was posted on usenet
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
	 * Get whether this release has a password - note that not all indexers 
	 * provide this functionality
	 * 
	 * @return whether this release is password protected
	 */
	public boolean getHasPassword() {
		return("0".equals(getItemAttribute(KEY_PASSWORD)));
	}

	/**
	 * Get the item's attribute if it exists (else return null)
	 * 
	 * @param name the key of the attribute to look up
	 * 
	 * @return the item's attribute (or null if it doesn't exist)
	 */
	public String getItemAttribute(String name) {
		if(null == name) {
			return(null);
		}

		for (ItemAttribute itemAttribute : itemAttributes) {
			ItemAttributes itemAttributesInner = itemAttribute.getItemAttributes();
			if(name.equals(itemAttributesInner.getName())) {
				return(itemAttributesInner.getValue());
			}
		}
		return(null);
	}

	@Override
	public Logger getLogger() { return(LOGGER); }
}
