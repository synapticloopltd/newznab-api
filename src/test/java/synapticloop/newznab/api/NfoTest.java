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
import synapticloop.newznab.api.response.RegistrationResponse;
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
