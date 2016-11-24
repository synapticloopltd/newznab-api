package synapticloop.newznab.api.response;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.newznab.api.exception.NewzNabApiException;

public class RegistrationResponse extends BaseReponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResponse.class);
	private final String password;
	private final String username;
	private final String apikey;

	public RegistrationResponse(String xml) throws NewzNabApiException {
		super(XML.toJSONObject(xml));
		JSONObject registerObject = this.readObject("register");
		this.password = this.readString(registerObject, "password");
		this.username = this.readString(registerObject, "username");
		this.apikey = this.readString(registerObject, "apikey");
	}

	@Override
	protected Logger getLogger() {
		return(LOGGER);
	}

	public String getPassword() { return this.password; }

	public String getUsername() { return this.username; }

	public String getApikey() { return this.apikey; }
}
