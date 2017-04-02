package synapticloop.newznab.api;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CommentResponse;
import synapticloop.newznab.api.response.CommentsResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.CommentItem;
import synapticloop.newznab.api.response.model.Item;

public class CommentTest {

	private NewzNabApi newzNabApi;

	@Before
	public void setup() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		initMocks(this);

		String newzNabApiUrl = System.getenv("NEWZNAB_API_URL");
		if(null == newzNabApiUrl) {
			newzNabApi = new NewzNabApi("http://lolo.sickbeard.com/api");
		} else {
			String newzNabApiKey = System.getenv("NEWZNAB_API_KEY");
			newzNabApi = new NewzNabApi(newzNabApiUrl, newzNabApiKey);
		}
	}

	@Test
	public void testComments() throws IOException, NewzNabApiException {
		// find a search result
		SearchResponse searchResponse = newzNabApi.search("tv", 0, 1, -1, false, false, null, null);
		assertNotNull(searchResponse);

		Item item = searchResponse.getItems().get(0);
		String guid = item.getGuid();
		String commentGuid = UUID.randomUUID().toString();
		String comment = "java NewzNab API test [" + commentGuid + "]";

		CommentResponse commentResponse = newzNabApi.addComment(guid, comment);
		assertNotNull(commentResponse);
		assertTrue(commentResponse.getId() > 0);

		CommentsResponse commentsResponse = newzNabApi.getComments(guid);
		List<CommentItem> items = commentsResponse.getItems();

		boolean foundComment = false;
		for (CommentItem commentItem : items) {
			if(comment.equals(commentItem.getComment())) {
				foundComment = true;
				break;
			}
		}

		assertTrue(foundComment);
	}
}
