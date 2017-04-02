package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedAttribute {
	@JsonProperty("id")  private long id;

	public long getId() { return id; }
}
