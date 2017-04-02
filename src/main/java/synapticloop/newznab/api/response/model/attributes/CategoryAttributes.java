package synapticloop.newznab.api.response.model.attributes;

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

import synapticloop.newznab.api.response.BaseModel;

public class CategoryAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryAttributes.class);

	@JsonProperty("id")      private int id;
	@JsonProperty("name")  private String name;

	/**
	 * Get the ID of the category, this maps to one of the search categories and 
	 * has the following pre-defined values: (for actual values that are supported
	 * by the NewzNab provider see the caps call).
	 * 
	 * <table>
	 * <thead>
	 * <tr>
	 * <th>Category</th><th>Range</th><th>Category</th><th>Name</th><th>Comments</th>
	 * <tr>
	 * </thead>
	 * <tbody>
	 * <tr><td>0000-0999</td><td>Reserved</td><td></td></tr>
	 * <tr><td>1000-1999</td><td>Console</td><td></td></tr>
	 * <tr><td>2000-2999</td><td>Movies</td><td></td></tr>
	 * <tr><td>3000-3999</td><td>Audio</td><td></td></tr>
	 * <tr><td>4000-4999</td><td>PC</td><td></td></tr>
	 * <tr><td>5000-5999</td><td>TV</td><td></td></tr>
	 * <tr><td>6000-6999</td><td>XXX</td><td></td></tr>
	 * <tr><td>7000-7999</td><td>Other</td><td></td></tr>
	 * <tr><td>8000-99999</td><td>Reserved</td><td>Reserved for future expansion</td></tr>
	 * <tr><td>100000-</td><td>Custom</td><td>Site specific category range. Defined in CAPS.</td></tr>
	 * </tbody>
	 * </table>
	 * 
	 * @return Return the id of the category
	 */
	public int getId() { return id; }

	/**
	 * Return the name of the category associated with the value
	 * 
	 * @return the name of the category associated with the value
	 */
	public String getName() { return name; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
