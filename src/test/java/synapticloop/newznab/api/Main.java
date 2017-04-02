package synapticloop.newznab.api;

import java.io.IOException;
import java.util.List;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CartResponse;
import synapticloop.newznab.api.response.CommentResponse;
import synapticloop.newznab.api.response.CommentsResponse;
import synapticloop.newznab.api.response.FeedResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.CommentItem;
import synapticloop.newznab.api.response.model.FeedItem;
import synapticloop.newznab.api.response.model.Item;

public class Main {
	private static final String NEWZNAB_API = "http://lolo.sickbeard.com/api";

	public static void main(String[] args) {
		// Set up the API call - normally you would use the following line, but
		// for example purposes - the lolo.sickbeard.com API will work
		// NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		NewzNabApi newzNabApi = new NewzNabApi(NEWZNAB_API);

		search(newzNabApi);

		// you can also get various feeds

		try {
			printFeedResponse("AUDIO", newzNabApi.getFeedForAudio());
			printFeedResponse("CONSOLES", newzNabApi.getFeedForConsoles());
			printFeedResponse("MOVIES", newzNabApi.getFeedForMovies());
			printFeedResponse("OTHER", newzNabApi.getFeedForOther());
			printFeedResponse("PC", newzNabApi.getFeedForPc());
			printFeedResponse("TV", newzNabApi.getFeedForTv());
			printFeedResponse("SITE", newzNabApi.getFeedForSite());
		} catch (IOException | NewzNabApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// now get some details
		try {
			SearchResponse searchResponse = getSearchResponse(newzNabApi, 0);
			// get the first result
			Item item = searchResponse.getItems().get(0);

			String guid = item.getGuid();

			// now that we have the guid - get some of the details
			String nfo = newzNabApi.getNfo(guid);
			System.out.println(nfo);

			CommentsResponse comments = newzNabApi.getComments(guid);
			List<CommentItem> commentItems = comments.getItems();
			System.out.println("Found " + commentItems.size() + " comments");
			for (CommentItem commentItem : commentItems) {
				System.out.println("  [COMMENT]: " + commentItem.getComment());
			}

			CommentResponse commentResponse = newzNabApi.addComment(guid, "We can add a comment");
			System.out.println("Added a comment with id: " + commentResponse.getId());

			// we can also add this to our cart
			CartResponse cartAddResponse = newzNabApi.cartAdd(guid);
			System.out.println("Cart add with id: " + cartAddResponse.getId());

			// we can also list the cart contents
			FeedResponse feedForCart = newzNabApi.getFeedForCart();
			List<FeedItem> feedItems = feedForCart.getFeedItems();
			for (FeedItem feedItem : feedItems) {
				System.out.println("Found the following in the cart, guid: " + feedItem.getGuid().getGuid() + ", title" +  feedItem.getTitle());
			}

			CartResponse cartDeleteResponse = newzNabApi.cartDelete(guid);
			System.out.println("Cart delete with guid: " + cartDeleteResponse.getId());

		} catch (NewzNabApiException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static void printFeedResponse(String group, FeedResponse feedResponse) {
		List<FeedItem> feedItems = feedResponse.getFeedItems();
		for (FeedItem feedItem : feedItems) {
			System.out.println("Found [" + group + "] item with title: " + feedItem.getTitle());
		}

	}

	private static void search(NewzNabApi newzNabApi) {

		try {
			SearchResponse searchResponse = getSearchResponse(newzNabApi, 0);

			System.out.println("Found " + searchResponse.getTotal() + " results for the search.");

			printResults(searchResponse);

			searchResponse = getSearchResponse(newzNabApi, 10);

			printResults(searchResponse);

		} catch (IOException | NewzNabApiException ex) {
			ex.printStackTrace();
		}
	}

	private static SearchResponse getSearchResponse(NewzNabApi newzNabApi, int offset) throws NewzNabApiException, IOException {
		SearchResponse searchResponse = newzNabApi.search(
				"wanted", // the search phrase
				offset, // the offset
				10, // the max number of results to return
				-1, // the number of days back to search - in this case all
				false, // delete from cart
				false, // return extended attributes
				null, // the categories to search in - in this case we are searching all
				null // the usenet groups to search in - all of them if null
				);
		return searchResponse;
	}

	private static void printResults(SearchResponse searchResponse) {
		List<Item> searchItems = searchResponse.getItems();
		for (Item item : searchItems) {
			System.out.println("  Found result in category: [" + item.getCategoryName() + "] with name: " + item.getTitle());
		}
	}
}
