package synapticloop.newznab.api;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.DetailsResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.Item;

public class DetailsTest {

	private NewzNabApi newzNabApi;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		initMocks(this);

		newzNabApi = new NewzNabApi("http://lolo.sickbeard.com/api");
	}

	@Test(expected=NewzNabApiException.class)
	public void testNullNfo() throws IOException, NewzNabApiException {
		newzNabApi.details(null);
	}

	@Test
	public void testBasicDetails() throws IOException, NewzNabApiException {
		// find a search result
		SearchResponse searchResponse = newzNabApi.search("tv", 0, 1, -1, false, false, null, null);
		assertNotNull(searchResponse);

		assertNotNull(searchResponse.getVersion());
		assertTrue(searchResponse.getOffset() == 0);
		assertTrue(searchResponse.getTotal() > 1);
		assertTrue(searchResponse.getItems().size() == 1);
		// now that we have got a 
		Item item = searchResponse.getItems().get(0);
		String guid = item.getGuid();
		DetailsResponse details = newzNabApi.details(guid);
		int size = details.getItems().size();
		assertEquals(size, 1);
	}
}
