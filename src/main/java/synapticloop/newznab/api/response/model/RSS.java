package synapticloop.newznab.api.response.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

@JsonIgnoreProperties(value = { "xmlns:newznab", "xmlns:atom" })
public class RSS extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RSS.class);

	@JsonProperty("channel")  private FeedChannel feedChannel;
	@JsonProperty("version")  private Float version;

	public FeedChannel getFeedChannel() { return(feedChannel); }
	public Float getVersion() { return(version); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
