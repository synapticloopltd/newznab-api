package synapticloop.newznab.api.response.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Channel extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Channel.class);

	@JsonProperty("title")        private String title;
	@JsonProperty("description")  private String description;
	@JsonProperty("link")         private String link;
	@JsonProperty("language")     private String language;
	@JsonProperty("webMaster")    private String webMaster;
	@JsonProperty("item")         private List<Item> items;
	@JsonProperty("image")        private Image image;
	@JsonProperty("response")     private Response response;

	// we never get the 'category' object back
	@JsonProperty("category")     private Object category;

	public String getTitle() { return title; }
	public String getDescription() { return description; }
	public String getLink() { return link; }
	public String getLanguage() { return language; }
	public String getWebMaster() { return webMaster; }
	public List<Item> getItems() { return items; }
	public Image getImage() { return image; }
	public Response getResponse() { return response; }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
