package synapticloop.newznab.api.response.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class Image extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Channel.class);

	@JsonProperty("url")  private String url;
	@JsonProperty("title")  private String title;
	@JsonProperty("link")  private String link;
	@JsonProperty("description")  private String description;

	@Override
	public Logger getLogger() { return(LOGGER); }

}
