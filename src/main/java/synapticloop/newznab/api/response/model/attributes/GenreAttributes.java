package synapticloop.newznab.api.response.model.attributes;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class GenreAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenreAttributes.class);

	@JsonProperty("id")      private int id;
	@JsonProperty("name")  private String name;
	@JsonProperty("categoryid")  private int category;

	@Override
	public Logger getLogger() { return(LOGGER); }

}
