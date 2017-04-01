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

public class Item extends BaseModel {
	private static final String GUID = "guid";

	private static final Logger LOGGER = LoggerFactory.getLogger(Item.class);

	@JsonProperty("title")        private String title;
	@JsonProperty("guid")         private String detailsLink;
	@JsonProperty("link")         private String nzbLink;
	@JsonProperty("comments")     private String commentsLink;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm:ss Z")
	@JsonProperty("pubDate")      private Date publishDate;

	@JsonProperty("category")     private String categoryName;
	@JsonProperty("description")  private String description;
	@JsonProperty("enclosure")    private Enclosure enclosure;
	@JsonProperty("attr")         private List<ItemAttribute> itemAttributes;

	public String getTitle() { return title; }
	public String getDetailsLink() { return detailsLink; }
	public String getNzbLink() { return nzbLink; }
	public String getCommentsLink() { return commentsLink; }
	public Date getPublishDate() { return publishDate; }
	public String getCategoryName() { return categoryName; }
	public String getDescription() { return description; }
	public Long getLength() { return(enclosure.getEnclosureAttributes().getLength()); }
	public String getType() { return(enclosure.getEnclosureAttributes().getType()); }

	public List<ItemAttribute> getItemAttributes() { return itemAttributes; }

	public String getGuid() {
		for (ItemAttribute itemAttribute : itemAttributes) {
			if(GUID.equals(itemAttribute.getName())) {
				return(itemAttribute.getValue());
			}
		}
		return(null);
	}

	@Override
	public Logger getLogger() { return(LOGGER); }
}
