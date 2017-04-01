package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import synapticloop.newznab.api.response.BaseModel;

public class SearchAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAttributes.class);

	private static final String YES = "yes";

	@JsonProperty("available")      private boolean isAvailable;
	@JsonProperty("supportedParams")  private List<String> supportedParams;


	@JsonSetter
	private void setAvailable(String value) {
		this.isAvailable = YES.equals(value);
	}

	public boolean getIsAvailable() { return isAvailable; }
	public List<String> getSupportedParams() { return supportedParams; }


	@Override
	public Logger getLogger() { return(LOGGER); }

}
