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
import synapticloop.newznab.api.response.bean.CategoryBean;
import synapticloop.newznab.api.response.bean.GenreBean;
import synapticloop.newznab.api.response.bean.GroupBean;

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

	private List<GenreBean> genres = new ArrayList<GenreBean>();
	private Map<Integer, List<GenreBean>> categoryGenreLookup = new HashMap<Integer, List<GenreBean>>();

	private List<GroupBean> groups = new ArrayList<GroupBean>();
	private Map<Integer, List<GroupBean>> idGroupLookup = new HashMap<Integer, List<GroupBean>>();

	private List<CategoryBean> categories = new ArrayList<CategoryBean>();
	private Map<Integer, CategoryBean> idCategoryLookup = new HashMap<Integer, CategoryBean>();

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
			GenreBean genreBean = new GenreBean(id, categoryId, name);
			List<GenreBean> list = categoryGenreLookup.get(categoryId);

			if(null == list) {
				list = new ArrayList<GenreBean>();
			}

			list.add(genreBean);
			categoryGenreLookup.put(categoryId, list);
			genres.add(genreBean);

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

			GroupBean groupBean = new GroupBean(id, name, description, lastUpdated);
			List<GroupBean> list = idGroupLookup.get(id);

			if(null == list) {
				list = new ArrayList<GroupBean>();
			}

			list.add(groupBean);

			idGroupLookup.put(id, list);
			groups.add(groupBean);

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

			CategoryBean categoryBean = new CategoryBean(id, name);
			idCategoryLookup.put(id, categoryBean);
			categories.add(categoryBean);

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

					CategoryBean subcategoryBean = new CategoryBean(subcatId, subcatName);
					categoryBean.addSubCategory(subcategoryBean);

					this.warnOnMissedKeys(subcatAttributes);
				}
			}

			this.warnOnMissedKeys(attributes);
		}

		this.warnOnMissedKeys(categoriesObject);
	}

	private boolean getBooleanAttributeFromObject(JSONObject searchingObject, String key) {
		JSONObject searchObject = this.readObject(searchingObject, key);
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
}
