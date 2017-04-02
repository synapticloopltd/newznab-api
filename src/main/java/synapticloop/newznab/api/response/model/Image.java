package synapticloop.newznab.api.response.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class Image extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Image.class);

	@JsonProperty("url")  private String url;
	@JsonProperty("title")  private String title;
	@JsonProperty("link")  private String link;
	@JsonProperty("description")  private String description;

	public String getUrl() { return url; }
	public String getTitle() { return title; }
	public String getLink() { return link; }
	public String getDescription() { return description; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
