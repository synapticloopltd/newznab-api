package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class LimitAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(LimitAttributes.class);

	@JsonProperty("max")      private int maxLimit;
	@JsonProperty("default")  private int defaultLimit;

	public int getMaxLimit() { return maxLimit; }
	public int  getdefaultLimit() { return defaultLimit; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
