package synapticloop.newznab.api.response;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.model.Register;

public class RegistrationResponse extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResponse.class);

	@JsonProperty("register")  private Register register;

	public String getPassword() { return this.register.getPassword(); }

	public String getUsername() { return this.register.getUsername(); }

	public String getApiKey() { return this.register.getApiKey(); }

	@Override
	public Logger getLogger() {
		return(LOGGER);
	}
}
