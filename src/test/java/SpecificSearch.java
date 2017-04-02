import java.io.IOException;

import synapticloop.newznab.api.NewzNabApi;
import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.SearchResponse;

public class SpecificSearch {

	public static void main(String[] args) throws IOException, NewzNabApiException {
		NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");

		// use the Book Search
		SearchResponse searchBook = newzNabApi.searchBook(
				"query", // the search term
				0, // the offset for the result
				100, // the maximum number of results to return
				"this is the title", // the title of the book
				"author's name", // the author
				100, // the maximum number of days to search back for
				false, // whether to delete from the cart 
				false // whether to return extended attributes in the results
				);

		// use the Movies Search
		newzNabApi.searchMovie(
				"query", // the search term
				"genre", // the genre of the film 
				0, // the offset for the result
				100, // the maximum number of results to return
				100, // the maximum number of days to search back for
				"imdbId", // the imdbId
				false, // whether to delete from the cart 
				false // whether to return extended attributes in the results
				);

		// use the TV Search 
		newzNabApi.searchTv(
				"query", // the search term
				1, // the season
				1,// the episode 
				0, // the offset for the result
				100, // the maximum number of results to return
				100, // the maximum number of days to search back for
				100, // the rage Id, 
				100, // the tvdb Id, 
				100, // the tv Maze Id, 
				false, // whether to delete from the cart 
				false // whether to return extended attributes in the results
				);

		newzNabApi.searchMusic(
				"query", // the search term
				0, // the offset for the result
				100, // the maximum number of results to return
				"album", // the album
				"artist", // the artist
				"label", // the label
				"track", // the track 
				2000, // the year of release
				new String[0], // the genres to search within
				new int[0] , // the array of categories
				100, // the maximum number of days to search back for
				false, // whether to delete from the cart 
				false // whether to return extended attributes in the results
				);

	}
}
