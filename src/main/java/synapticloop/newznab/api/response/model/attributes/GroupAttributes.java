package synapticloop.newznab.api.response.model.attributes;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class GroupAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupAttributes.class);

	@JsonProperty("id")      private int id;
	@JsonProperty("name")  private String name;
	@JsonProperty("description")  private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm:ss Z")
	@JsonProperty("lastupdate")  private Date lastUpdate;

	@Override
	public Logger getLogger() { return(LOGGER); }

}
