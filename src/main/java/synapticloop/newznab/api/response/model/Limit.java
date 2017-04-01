package synapticloop.newznab.api.response.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.attributes.LimitAttributes;
import synapticloop.newznab.api.response.model.attributes.ServerAttributes;

public class Limit extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Limit.class);

	@JsonProperty("@attributes")  private LimitAttributes limitAttributes;

	@Override
	public Logger getLogger() { return(LOGGER); }
}
