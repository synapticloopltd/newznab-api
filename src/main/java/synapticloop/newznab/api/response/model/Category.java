package synapticloop.newznab.api.response.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;
import synapticloop.newznab.api.response.model.attributes.CategoryAttributes;

public class Category extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Category.class);

	@JsonProperty("@attributes")  private CategoryAttributes categoryAttributes;
	@JsonProperty("subcat") private List<SubCategory> subCaterories;

	@Override
	public Logger getLogger() { return(LOGGER); }
}
