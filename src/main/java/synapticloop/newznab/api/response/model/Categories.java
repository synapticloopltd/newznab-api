package synapticloop.newznab.api.response.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class Categories extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Categories.class);

	@JsonProperty("category")  private List<Category> categories;

	@Override
	public Logger getLogger() { return(LOGGER); }
}