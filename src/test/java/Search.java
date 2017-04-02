import java.io.IOException;

import synapticloop.newznab.api.Category;
import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.SearchResponse;

public class Search {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		SearchResponse searchResponse = newzNabApi.search("something", 0, new int[] { 
				Category.CATEGORY_AUDIO_AUDIOBOOK, 
				Category.CATEGORY_AUDIO_VIDEO}
				);

		// now do something with the results
	}
}
