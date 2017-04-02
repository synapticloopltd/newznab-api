package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class CartAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartAttributes.class);

	@JsonProperty("id")  private String id;

	public String getId() { return id; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
