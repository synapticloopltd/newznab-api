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
	@JsonProperty("newznab:attr")  private List<ItemAttributes> itemAttributes;

	public String getTitle() { return title; }
	public String getNzbLink() { return nzbLink; }
	public String getCommentsLink() { return commentsLink; }
	public Date getPublishDate() { return publishDate; }
	public String getCategoryName() { return categoryName; }
	public String getDescription() { return description; }


	public List<ItemAttributes> getItemAttributes() { return itemAttributes; }

	public String getPoster() { return(getItemAttribute("poster")); }

	public String getGroup() { return(getItemAttribute("group")); }

	public int getNumGrabs() { 
		String itemAttribute = getItemAttribute("grabs");
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
		String itemAttribute = getItemAttribute("files");
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
		String itemAttribute = getItemAttribute("comments");
		if(null != itemAttribute) {
			try {
				return(Integer.parseInt(itemAttribute));
			} catch(NumberFormatException ex) {
				LOGGER.error("Could not parse number of comments for value '{}'", itemAttribute);
			}
		}
		return 0; 
	}

	public Guid getGuid() { return(guid); }

	public Date getUsenetDate() {
		String itemAttribute = getItemAttribute("usenetdate");
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
		return("0".equals(getItemAttribute("password")));
	}

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
