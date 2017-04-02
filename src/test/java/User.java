import java.io.IOException;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.UserResponse;

public class User {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		UserResponse userResponse = newzNabApi.getUser("your_username");

		System.out.println("username: " + userResponse.getUsername());
		System.out.println("    role: " + userResponse.getRole());
		System.out.println("  joined: " + userResponse.getCreatedDate());

		System.out.println("     number of api requests allowed per day: " + userResponse.getNumApiRequests());
		System.out.println("number of download requests allowed per day: " + userResponse.getNumDownloadRequests());
		System.out.println("                 number of grabbed releases: " + userResponse.getNumGrabs());


		System.out.println("has a console view?: " + userResponse.getHasConsoleView());
		System.out.println("  has a movie view?: " + userResponse.getHasMovieView());
		System.out.println("  has a music view?: " + userResponse.getHasMusicView());
	}
}
