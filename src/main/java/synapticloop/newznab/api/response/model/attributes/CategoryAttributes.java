package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class CategoryAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryAttributes.class);

	@JsonProperty("id")      private int id;
	@JsonProperty("name")  private String name;

	@Override
	public Logger getLogger() { return(LOGGER); }

}
