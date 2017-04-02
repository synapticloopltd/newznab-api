import java.io.IOException;
import java.util.List;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CommentsResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.CommentItem;
import synapticloop.newznab.api.response.model.Item;

public class Comments {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		SearchResponse searchResponse = newzNabApi.search("something");
		List<Item> items = searchResponse.getItems();
		Item item = items.get(0);
		String guid = item.getGuid();

		// list all of the comments
		CommentsResponse commentsResponse = newzNabApi.getComments(guid);
		List<CommentItem> commentItems = commentsResponse.getItems();
		for (CommentItem commentItem : commentItems) {
			System.out.println("Found a comment: " + commentItem.getComment());
		}

		// now add a new comment
		newzNabApi.addComment(guid, "This is a comment on a release");
	}
}
