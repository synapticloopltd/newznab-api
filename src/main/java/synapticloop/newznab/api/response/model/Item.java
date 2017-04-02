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

	public String getTitle() { return title; }
	public String getDetailsLink() { return detailsLink; }
	public String getNzbLink() { return nzbLink; }
	public String getCommentsLink() { return commentsLink; }
	public Date getPublishDate() { return publishDate; }
	public String getCategoryName() { return categoryName; }
	public String getDescription() { return description; }
	public Long getLength() { return(enclosure.getEnclosureAttributes().getLength()); }
	public String getType() { return(enclosure.getEnclosureAttributes().getType()); }


	public List<ItemAttribute> getItemAttributes() { return itemAttributes; }

	public String getPoster() { return(getItemAttribute(KEY_POSTER)); }

	public String getGroup() { return(getItemAttribute(KEY_GROUP)); }

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

	public String getGuid() { return(getItemAttribute(GUID)); }

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

	public boolean getHasPassword() {
		return("0".equals(getItemAttribute(KEY_PASSWORD)));
	}

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
