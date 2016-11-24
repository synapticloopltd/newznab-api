package synapticloop.newznab.api.response.bean;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class GroupBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupBean.class);

	private final Integer id;
	private final String name;
	private final String description;
	private final Date lastUpdate;

	public GroupBean(Integer id, String name, String description, Date lastUpdate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.lastUpdate = lastUpdate;
	}

	public Integer getId() { return this.id; }

	public String getName() { return this.name; }

	public Date getLastUpdate() { return this.lastUpdate; }

	public String getDescription() { return this.description; }


}
