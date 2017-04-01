package synapticloop.newznab.api.response.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class ResponseAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAttributes.class);

	@JsonProperty("offset")  private Long offset;
	@JsonProperty("total")  private Long total;

	public Long getOffset() { return offset; }
	public Long getTotal() { return total; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
