package synapticloop.newznab.api.response.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class EnclosureAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnclosureAttributes.class);

	@JsonProperty("url")     private String url;
	@JsonProperty("length")  private Long length;
	@JsonProperty("type")    private String type;


	public String getUrl() { return url; }
	public Long getLength() { return length; }
	public String getType() { return type; }


	@Override
	public Logger getLogger() { return(LOGGER); }

}
