package synapticloop.newznab.api.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
 * Copyright (c) 2016 Synapticloop.
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.bean.Category;
import synapticloop.newznab.api.response.bean.Genre;
import synapticloop.newznab.api.response.bean.Group;

public class CapsResponse extends BaseReponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(CapsResponse.class);

	// Wed, 23 Nov 2016 19:51:36 -0500
	private static final String SIMPLE_DATE_FORMAT_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(SIMPLE_DATE_FORMAT_PATTERN);

	private static final String ATTRIBUTES = "@attributes";


	private String serverAppversion;
	private String serverVersion;
	private String serverTitle;
	private String serverStrapline;
	private String serverEmail;
	private String serverUrl;
	private String serverImage;

	private Integer limitsMax;
	private Integer limitsDefault;

	private boolean searchAvailable;
	private boolean searchTvAvailable;
	private boolean searchAudioAvailable;
	private boolean searchMovieAvailable;

	private boolean registrationAvailable;
	private boolean registrationOpen;

	private List<Genre> genres = new ArrayList<Genre>();
	private Map<Integer, List<Genre>> categoryGenreLookup = new HashMap<Integer, List<Genre>>();

	private List<Group> groups = new ArrayList<Group>();
	private Map<Integer, Group> idGroupLookup = new HashMap<Integer, Group>();

	private List<Category> categories = new ArrayList<Category>();
	private Map<Integer, Category> idCategoryLookup = new HashMap<Integer, Category>();

	public CapsResponse(String json) throws NewzNabApiException {
		super(json);

		this.parseServer(this.readObject(response, "server"));
		this.parseLimits(this.readObject(response, "limits"));
		this.parseGenres(this.readObject(response, "genres"));
		this.parseSearching(this.readObject(response, "searching"));
		this.parseRegistration(this.readObject(response, "registration"));
		this.parseGroups(this.readObject(response, "groups"));
		this.parseCategories(this.readObject(response, "categories"));

		this.warnOnMissedKeys(response);
	}

	private void parseServer(JSONObject serverObject) {
		JSONObject attributes = getAttributesfromObject(serverObject);

		this.serverAppversion = this.readString(attributes, "appversion");
		this.serverVersion = this.readString(attributes, "version");
		this.serverTitle = this.readString(attributes, "title");
		this.serverStrapline = this.readString(attributes, "strapline");
		this.serverEmail = this.readString(attributes, "email");
		this.serverUrl = this.readString(attributes, "url");
		this.serverImage = this.readString(attributes, "image");

		this.warnOnMissedKeys(attributes);

	}

	private void parseLimits(JSONObject limitsObject) {
		JSONObject attributes = getAttributesfromObject(limitsObject);

		this.limitsMax = this.readInt(attributes, "max");
		this.limitsDefault = this.readInt(attributes, "default");

		this.warnOnMissedKeys(attributes);
	}

	private void parseGenres(JSONObject genresObject) {
		JSONArray genreArray = this.readObjects(genresObject, "genre");
		if(null == genreArray) {
			LOGGER.warn("No genres returned from indexer");
			return;
		}

		for (Object object : genreArray) {
			JSONObject genreObject = (JSONObject)object;
			JSONObject attributes = getAttributesfromObject(genreObject);
			Integer id = this.readInt(attributes, "id");
			Integer categoryId = this.readInt(attributes, "categoryid");
			String name = this.readString(attributes, "name");
			Genre genre = new Genre(id, categoryId, name);
			List<Genre> list = categoryGenreLookup.get(categoryId);

			if(null == list) {
				list = new ArrayList<Genre>();
			}

			list.add(genre);
			categoryGenreLookup.put(categoryId, list);
			genres.add(genre);

			this.warnOnMissedKeys(attributes);
		}
	}

	private void parseSearching(JSONObject searchingObject) {
		this.searchAvailable = getBooleanAttributeFromObject(searchingObject, "search");
		this.searchTvAvailable = getBooleanAttributeFromObject(searchingObject, "tv-search");
		this.searchAudioAvailable = getBooleanAttributeFromObject(searchingObject, "audio-search");
		this.searchMovieAvailable = getBooleanAttributeFromObject(searchingObject, "movie-search");

		this.warnOnMissedKeys(searchingObject);
	}

	private void parseRegistration(JSONObject registrationObject) {
		JSONObject attributes = getAttributesfromObject(registrationObject);
		this.registrationAvailable = "yes".equals(this.readString(attributes, "available"));
		this.registrationOpen = "yes".equals(this.readString(attributes, "open"));
	}

	private void parseGroups(JSONObject groupsObject) {
		JSONArray groupArray = this.readObjects(groupsObject, "group");

		if(null == groupArray) {
			LOGGER.warn("No groups returned from indexer");
			return;
		}

		for (Object object : groupArray) {
			JSONObject groupObject = (JSONObject)object;
			JSONObject attributes = getAttributesfromObject(groupObject);

			Integer id = this.readInt(attributes, "id");
			String name = this.readString(attributes, "name");
			String description = this.readString(attributes, "description");
			String dateTemp = this.readString(attributes, "lastupdate");

			Date lastUpdated = null;
			try {
				lastUpdated = SIMPLE_DATE_FORMAT.parse(dateTemp);
			} catch (ParseException ex) {
				LOGGER.error(String.format("Could not format date for '%s'", dateTemp));
			}

			Group group = new Group(id, name, description, lastUpdated);
			idGroupLookup.put(id, group);
			groups.add(group);

			this.warnOnMissedKeys(attributes);
		}
	}

	private void parseCategories(JSONObject categoriesObject) {
		JSONArray categoryArray = this.readObjects(categoriesObject, "category");

		if(null == categoryArray) {
			LOGGER.warn("No categories returned from indexer");
			return;
		}

		for (Object object : categoryArray) {
			JSONObject categoryObject = (JSONObject)object;
			JSONObject attributes = getAttributesfromObject(categoryObject);

			Integer id = this.readInt(attributes, "id");
			String name = this.readString(attributes, "name");

			Category category = new Category(id, name);
			idCategoryLookup.put(id, category);
			categories.add(category);

			// now we need to go through the sub-categories
			JSONArray subcatArray = this.readObjects(categoryObject, "subcat");
			if(null == subcatArray) {
				LOGGER.warn(String.format("No sub categories found for category '%s'", name));
			} else {
				for (Object objectSubcat : subcatArray) {
					JSONObject subcatObject = (JSONObject)objectSubcat;
					JSONObject subcatAttributes = getAttributesfromObject(subcatObject);

					Integer subcatId = this.readInt(subcatAttributes, "id");
					String subcatName = this.readString(subcatAttributes, "name");

					Category subcategory = new Category(subcatId, subcatName);
					category.addSubCategory(subcategory);

					this.warnOnMissedKeys(subcatAttributes);
				}
			}

			this.warnOnMissedKeys(attributes);
		}

		this.warnOnMissedKeys(categoriesObject);
	}

	private boolean getBooleanAttributeFromObject(JSONObject searchingObject, String key) {
		JSONObject searchObject = this.readObject(searchingObject, key);

		if(null == searchObject) {
			return(false);
		}

		JSONObject searchObjectAttributes = getAttributesfromObject(searchObject);
		return("yes".equals(this.readString(searchObjectAttributes, "available")));
	}

	private JSONObject getAttributesfromObject(JSONObject jsonObject) {
		return(this.readObject(jsonObject, ATTRIBUTES));
	}

	@Override
	protected Logger getLogger() {
		return(LOGGER);
	}

	public String getServerAppversion() { return this.serverAppversion; }

	public String getServerVersion() { return this.serverVersion; }

	public String getServerTitle() { return this.serverTitle; }

	public String getServerStrapline() { return this.serverStrapline; }

	public String getServerEmail() { return this.serverEmail; }

	public String getServerUrl() { return this.serverUrl; }

	public String getServerImage() { return this.serverImage; }

	
	
	public Integer getLimitsMax() { return this.limitsMax; }

	public Integer getLimitsDefault() { return this.limitsDefault; }

	
	
	public boolean getSearchAvailable() { return this.searchAvailable; }

	public boolean getSearchTvAvailable() { return this.searchTvAvailable; }

	public boolean getSearchAudioAvailable() { return this.searchAudioAvailable; }

	public boolean getSearchMovieAvailable() { return this.searchMovieAvailable; }

	
	
	public boolean getRegistrationAvailable() { return this.registrationAvailable; }

	public boolean getRegistrationOpen() { return this.registrationOpen; }


	public List<Genre> getGenres() { return this.genres; }

	public List<Genre> getGenresByCategoryId(Integer categoryId) { return(categoryGenreLookup.get(categoryId)); }

	
	
	public List<Group> getGroups() { return this.groups; }

	public Group getGroupById(Integer id) { return(idGroupLookup.get(id)); }

	
	
	public List<Category> getCategories() { return this.categories; }

	public Category getCategoryById(Integer id) { return(idCategoryLookup.get(id)); }
}
