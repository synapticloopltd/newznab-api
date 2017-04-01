package synapticloop.newznab.api.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseModel {
	@JsonIgnore protected Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public abstract Logger getLogger();

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		getLogger().warn("No native setter for key '{}' with value '{}'", name, value);
		this.additionalProperties.put(name, value);
	}

}
