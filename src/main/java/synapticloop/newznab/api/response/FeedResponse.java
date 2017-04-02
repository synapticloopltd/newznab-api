package synapticloop.newznab.api.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.model.Channel;
import synapticloop.newznab.api.response.model.FeedItem;
import synapticloop.newznab.api.response.model.Image;
import synapticloop.newznab.api.response.model.Item;
import synapticloop.newznab.api.response.model.RSS;
import synapticloop.newznab.api.response.model.attributes.Attribute;
import synapticloop.newznab.api.response.model.attributes.CartAttributes;
import synapticloop.newznab.api.response.model.attributes.FeedAttribute;

public class FeedResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedResponse.class);

	@JsonProperty("@attributes") private FeedAttribute feedAttribute;

	@JsonProperty("rss")  private RSS rss;

	public long getId() { return(feedAttribute.getId()); }
	public Float getVersion() { return(rss.getVersion()); }

	public String getDescription() { return(rss.getFeedChannel().getDescription()); }
	public Image getImage() { return(rss.getFeedChannel().getImage()); }
	public String getLanguage() { return(rss.getFeedChannel().getLanguage()); }
	public String getSiteLink() { return(rss.getFeedChannel().getSiteLink()); }
	public String getTitle() { return(rss.getFeedChannel().getTitle()); }
	public String getWebMaster() { return(rss.getFeedChannel().getWebMaster()); }
	public List<FeedItem> getFeedItems() { return(rss.getFeedChannel().getFeedItems()); }

	@Override
	public Logger getLogger() { return(LOGGER); }
}
