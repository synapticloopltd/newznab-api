package synapticloop.newznab.api.response.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribute {
	@JsonProperty("version")  private Float version;

	public Float getVersion() { return version; }
}
