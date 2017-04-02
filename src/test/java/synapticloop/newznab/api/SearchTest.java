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

public class SearchTest {

	private NewzNabApi newzNabApi;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		initMocks(this);

		newzNabApi = new NewzNabApi("http://lolo.sickbeard.com/api");
	}

	@Test
	public void testBasicSearch() throws IOException, NewzNabApiException {
		SearchResponse searchResponse = newzNabApi.search("tv");
		assertNotNull(searchResponse);

		assertNotNull(searchResponse.getVersion());
		assertTrue(searchResponse.getOffset() == 0);
		assertTrue(searchResponse.getTotal() > 0);
		assertTrue(searchResponse.getItems().size() > 0);
	}
}
