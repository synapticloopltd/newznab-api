package synapticloop.newznab.api.response.bean;

import java.util.ArrayList;
import java.util.List;

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

public class Category {
	private final Integer id;
	private final String name;

	private final List<Category> subCategoryBeans = new ArrayList<Category>();

	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addSubCategory(Category categoryBean) {
		subCategoryBeans.add(categoryBean);
	}

	public Integer getId() { return this.id; }

	public String getName() { return this.name; }
}
