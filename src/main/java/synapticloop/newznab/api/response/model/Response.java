package synapticloop.newznab.api.response.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class Response extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Response.class);

	@JsonProperty("@attributes")  private ResponseAttributes responseAttributes;

	public ResponseAttributes getResponseAttributes() { return(responseAttributes); }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
