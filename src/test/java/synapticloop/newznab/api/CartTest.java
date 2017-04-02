package synapticloop.newznab.api;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CapabilitiesResponse;
import synapticloop.newznab.api.response.CartResponse;
import synapticloop.newznab.api.response.FeedResponse;
import synapticloop.newznab.api.response.RegistrationResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.FeedItem;
import synapticloop.newznab.api.response.model.Item;

public class CartTest {

	private NewzNabApi newzNabApi;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		initMocks(this);

		String newzNabApiUrl = System.getenv("NEWZNAB_API_URL");
		if(null == newzNabApiUrl) {
			newzNabApi = new NewzNabApi("http://lolo.sickbeard.com/api");
		} else {
			String newzNabApiKey = System.getenv("NEWZNAB_API_KEY");
			newzNabApi = new NewzNabApi(newzNabApiUrl, newzNabApiKey);
		}
	}

	@Test
	public void testCart() throws IOException, NewzNabApiException {
		// find a search result
		SearchResponse searchResponse = newzNabApi.search("tv", 0, 1, -1, false, false, null, null);
		assertNotNull(searchResponse);

		assertNotNull(searchResponse.getVersion());
		assertTrue(searchResponse.getOffset() == 0);
		assertTrue(searchResponse.getTotal() > 1);
		assertTrue(searchResponse.getItems().size() == 1);
		Item item = searchResponse.getItems().get(0);
		String guid = item.getGuid();
		CartResponse cartAddResponse = newzNabApi.cartAdd(guid);
		assertNotNull(cartAddResponse);

		// get the cart

		FeedResponse feedResponse = newzNabApi.getFeedForCart();
		List<FeedItem> feedItems = feedResponse.getFeedItems();
		boolean isInCart = false;
		for (FeedItem feedItem : feedItems) {
			if(guid.equals(feedItem.getGuid().getGuid())) {
				isInCart = true;
				break;
			}
		}

		assertTrue(isInCart);

		// we need to sleep for a little bit - to ensure that the cart has been 
		// updated - this is not a good way to do things 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now delete the item from the cart
		CartResponse cartDeleteResponse = newzNabApi.cartDelete(guid);
		assertNotNull(cartDeleteResponse);

		// check that the guid is not in the cart
		feedResponse = newzNabApi.getFeedForCart();
		feedItems = feedResponse.getFeedItems();
		isInCart = false;
		for (FeedItem feedItem : feedItems) {
			if(guid.equals(feedItem.getGuid())) {
				isInCart = true;
			}
		}
		assertFalse(isInCart);
	}
}
