import java.io.IOException;
import java.util.List;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.FeedResponse;
import synapticloop.newznab.api.response.model.FeedItem;

public class GetFeedForTv {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		FeedResponse feedResponse = newzNabApi.getFeedForTv();

		List<FeedItem> feedItems = feedResponse.getFeedItems();
		for (FeedItem feedItem : feedItems) {
			System.out.println("Found an item in the 'TV' section: " + feedItem.getTitle());
		}

	}

}
