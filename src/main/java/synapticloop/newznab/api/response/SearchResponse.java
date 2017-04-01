package synapticloop.newznab.api.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.bean.Attribute;
import synapticloop.newznab.api.response.model.Channel;
import synapticloop.newznab.api.response.model.Image;
import synapticloop.newznab.api.response.model.Item;

public class SearchResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchResponse.class);

	@JsonProperty("@attributes")  private Attribute attribute;
	@JsonProperty("channel")  private Channel channel;

	public Float getVersion() { return(attribute.getVersion()); }
	public String getTitle() { return(channel.getTitle()); }
	public String getDescription() { return(channel.getDescription()); }
	public String getLink() { return(channel.getLink()); }
	public String getLanguage() { return(channel.getLanguage()); }
	public String getWebMaster() { return(channel.getWebMaster()); }
	public List<Item> getItems() { return(channel.getItems()); }
	public Image getImage() { return(channel.getImage()); }
	
	public Long getOffset() { return(channel.getResponse().getResponseAttributes().getOffset()); }
	public Long getTotal() { return(channel.getResponse().getResponseAttributes().getTotal()); }
	
	@Override
	public Logger getLogger() { return(LOGGER); }
}
