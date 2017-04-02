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
import synapticloop.newznab.api.response.UserResponse;

public class UserTest {

	private NewzNabApi newzNabApi;
	private String newzNabUsername;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		initMocks(this);

		String newzNabApiUrl = System.getenv("NEWZNAB_API_URL");
		newzNabUsername = System.getenv("NEWZNAB_API_USERNAME");
		if(null == newzNabApiUrl) {
			newzNabApi = new NewzNabApi("http://lolo.sickbeard.com/api");
		} else {
			String newzNabApiKey = System.getenv("NEWZNAB_API_KEY");
			newzNabApi = new NewzNabApi(newzNabApiUrl, newzNabApiKey);
		}
	}

	@Test
	public void testGetUser() throws IOException, NewzNabApiException {
		UserResponse userResponse = newzNabApi.getUser(newzNabUsername);
		assertEquals(newzNabUsername, userResponse.getUsername());
	}
}
