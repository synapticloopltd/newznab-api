import java.io.IOException;
import java.util.List;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CapabilitiesResponse;
import synapticloop.newznab.api.response.model.Genre;
import synapticloop.newznab.api.response.model.Group;
import synapticloop.newznab.api.response.model.attributes.GenreAttributes;
import synapticloop.newznab.api.response.model.attributes.GroupAttributes;

public class Capabilities {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		CapabilitiesResponse capabilitiesResponse = newzNabApi.getCapabilities();

		// test whether the NewzNab indexer has specific searches
		capabilitiesResponse.getHasAudioSearch();
		capabilitiesResponse.getHasMovieSearch();
		capabilitiesResponse.getHasTvSearch();
		capabilitiesResponse.getHasBookSearch();

		// get a list of the genres that the indexer has
		List<Genre> genres = capabilitiesResponse.getGenres();
		for (Genre genre : genres) {
			List<GenreAttributes> genreAttributes = genre.getGenreAttributes();
			for (GenreAttributes genreAttributesInner : genreAttributes) {
				genreAttributesInner.getName(); // the name of the genre
				genreAttributesInner.getCategory(); // the category which this genre relates to
			}
		}

		// get a list of the groups
		List<Group> groups = capabilitiesResponse.getGroups();
		for (Group group : groups) {
			List<GroupAttributes> groupAttributes = group.getGroupAttributes();
			for (GroupAttributes groupAttributesInner : groupAttributes) {
				groupAttributesInner.getName(); // the name of the usenet group
				groupAttributesInner.getLastUpdate();  // when this group was last updated
			}
		}

		// registration options
		capabilitiesResponse.getIsRegistrationAvailable(); // is registration available
		capabilitiesResponse.getIsRegistrationOpen();  // is registration open

		capabilitiesResponse.getDefaultResultsLimit(); // the default number of results that are returned through a search or a feed
		capabilitiesResponse.getMaxResultsLimit(); // the maximum results that can be returned through a search or a feed
	}
}
