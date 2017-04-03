package synapticloop.newznab.api.response.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class Register extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);

	@JsonProperty("password")  private String password;
	@JsonProperty("username")  private String username;
	@JsonProperty("apikey")    private  String apiKey;

/**
 * Get the password for the user
 * 
 * @return the password for the user
 */
	public String getPassword() { return this.password; }

	/**
	 * Get the username
	 * 
	 * @return the username
	 */
	public String getUsername() { return this.username; }

	/**
	 * Get the api key for the user
	 * 
	 * @return the api key for the user
	 */
	public String getApiKey() { return this.apiKey; }

	@Override
	public Logger getLogger() {
		return(LOGGER);
	}
}
