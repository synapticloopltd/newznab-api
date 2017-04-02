package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class ItemAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemAttributes.class);

	@JsonProperty("name")   private String name;
	@JsonProperty("value")   private String value;

	public String getName() { return name; }
	public String getValue() { return value; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
