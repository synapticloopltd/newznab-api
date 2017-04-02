package synapticloop.newznab.api.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.model.Channel;
import synapticloop.newznab.api.response.model.Image;
import synapticloop.newznab.api.response.model.Item;
import synapticloop.newznab.api.response.model.RSS;
import synapticloop.newznab.api.response.model.attributes.Attribute;
import synapticloop.newznab.api.response.model.attributes.CartAttributes;
import synapticloop.newznab.api.response.model.attributes.FeedAttribute;

public class CartResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartResponse.class);

	@JsonProperty("@attributes") private CartAttributes cartAttributes;

	public String getId() { return(cartAttributes.getId()); }
	@Override
	public Logger getLogger() { return(LOGGER); }
}
