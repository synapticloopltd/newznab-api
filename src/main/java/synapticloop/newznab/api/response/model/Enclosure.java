package synapticloop.newznab.api.response.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class Enclosure extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Channel.class);

	@JsonProperty("@attributes")  private EnclosureAttributes enclosureAttributes;

	public EnclosureAttributes getEnclosureAttributes() { return(enclosureAttributes); }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
