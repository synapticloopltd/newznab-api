package synapticloop.newznab.api.response.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class Searching extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Searching.class);

	@JsonProperty("search")  private Search search;
	@JsonProperty("tv-search")  private Search tvSearch;
	@JsonProperty("movie-search")  private Search movieSearch;
	@JsonProperty("audio-search")  private Search audioSearch;
//	@JsonProperty("book-search")  private Search bookSearch;

	public boolean getHasSearch() {
		return(hasSearch(search));
	}

	public boolean getHasTvSearch() {
		return(hasSearch(tvSearch));
	}

	public boolean getHasMovieSearchTv() {
		return(hasSearch(movieSearch));
	}

	public boolean getHasAudioSearchTv() {
		return(hasSearch(audioSearch));
	}

	private boolean hasSearch(Search search) {
		return(null != search && search.getSearchAttributes().getIsAvailable());
	}
	@Override
	public Logger getLogger() { return(LOGGER); }
}
