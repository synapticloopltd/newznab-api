package synapticloop.newznab.api.response.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;
import synapticloop.newznab.api.response.model.attributes.SearchAttributes;

public class Search extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Search.class);

	@JsonProperty("@attributes")  private SearchAttributes searchAttributes;

	public SearchAttributes getSearchAttributes() { return searchAttributes; }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
