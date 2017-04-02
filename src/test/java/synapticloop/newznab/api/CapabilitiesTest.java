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
import synapticloop.newznab.api.response.CapabilitiesResponse;

public class CapabilitiesTest {

	private NewzNabApi newzNabApi;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		initMocks(this);

		newzNabApi = new NewzNabApi("http://lolo.sickbeard.com/api");
	}

	@Test
	public void testGetNfo() throws IOException, NewzNabApiException {
		CapabilitiesResponse capabilitiesResponse = newzNabApi.getCapabilities();
		assertNotNull(capabilitiesResponse);

	}
}
