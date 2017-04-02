import java.io.IOException;
import java.util.List;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.Item;

public class SearchIteration {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		SearchResponse searchResponse = null; 

		// get the total number of results
		Long total = Long.MAX_VALUE;
		int offset = 0;
		while(offset < total) {
			searchResponse = newzNabApi.search("something", offset);
			printResults(searchResponse);
			total = searchResponse.getTotal();
			offset = offset + 100;
		}
	}

	private static void printResults(SearchResponse searchResponse) {
		List<Item> items = searchResponse.getItems();
		for (Item item : items) {
			System.out.println("Found an item: " + item.getTitle());
		}
	}

}
