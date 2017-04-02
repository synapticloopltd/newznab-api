package synapticloop.newznab.api.response.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import synapticloop.newznab.api.response.BaseModel;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonIgnoreProperties({"atom:link"})
public class FeedChannel extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedChannel.class);

//	@JsonProperty("atom:link")        private String title;
	@JsonProperty("title")        private String title;
	@JsonProperty("description")  private String description;
	@JsonProperty("link")         private String siteLink;
	@JsonProperty("language")     private String language;
	@JsonProperty("webMaster")    private String webMaster;
	@JsonProperty("item")         private List<FeedItem> items;
	@JsonProperty("image")        private Image image;

	// we never get the 'category' object back
	@JsonProperty("category")     private Object category;

	public String getTitle() { return title; }
	public String getDescription() { return description; }
	public String getSiteLink() { return siteLink; }
	public String getLanguage() { return language; }
	public String getWebMaster() { return webMaster; }
	public List<FeedItem> getFeedItems() { return items; }
	public Image getImage() { return image; }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
