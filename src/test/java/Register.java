import java.io.IOException;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.RegistrationResponse;

public class Register {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		RegistrationResponse registrationResponse = newzNabApi.register("something@example.com");

		System.out.println("Congratulations, you have just registered with the following details:");
		System.out.println("  username: " + registrationResponse.getUsername());
		System.out.println("  password: " + registrationResponse.getPassword());
		System.out.println("   api key: " + registrationResponse.getApiKey());
	}
}
