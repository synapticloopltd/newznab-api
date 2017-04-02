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
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.Item;

public class NfoTest {

	private NewzNabApi newzNabApi;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		initMocks(this);

		newzNabApi = new NewzNabApi("http://lolo.sickbeard.com/api");
	}

	@Test(expected=NewzNabApiException.class)
	public void testNullGuid() throws IOException, NewzNabApiException {
		newzNabApi.getNzb(null);
	}

	@Test
	public void testGetNfo() throws IOException, NewzNabApiException {
		// find a search result
		SearchResponse searchResponse = newzNabApi.search("tv", 0, 1, -1, false, false, null, null);
		assertNotNull(searchResponse);

		assertNotNull(searchResponse.getVersion());
		assertTrue(searchResponse.getOffset() == 0);
		assertTrue(searchResponse.getTotal() > 1);
		assertTrue(searchResponse.getItems().size() == 1);
		Item item = searchResponse.getItems().get(0);
		String guid = item.getGuid();
		String nzb = newzNabApi.getNzb(guid);
		assertNotNull(nzb);
	}
}
