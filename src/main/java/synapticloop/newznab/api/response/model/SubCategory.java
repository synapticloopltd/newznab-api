package synapticloop.newznab.api.response.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;
import synapticloop.newznab.api.response.model.attributes.CategoryAttributes;

public class SubCategory extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubCategory.class);

	@JsonProperty("@attributes")  private CategoryAttributes categoryAttributes;

	@Override
	public Logger getLogger() { return(LOGGER); }
}
