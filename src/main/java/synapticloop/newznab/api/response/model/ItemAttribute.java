package synapticloop.newznab.api.response.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class ItemAttribute extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemAttribute.class);

	private String name = null;
	private String value = null;

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.name = name;
		this.value = value.toString();
	}

	public String getName() { return name; }
	public String getValue() { return value; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
