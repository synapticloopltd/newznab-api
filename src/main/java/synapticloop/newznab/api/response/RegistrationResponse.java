package synapticloop.newznab.api.response;

import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.newznab.api.exception.NewzNabApiException;

public class RegistrationResponse extends BaseReponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationResponse.class);

	public RegistrationResponse(String xml) throws NewzNabApiException {
		super(XML.toJSONObject(xml));
		System.out.println(response.toString());
	}

	@Override
	protected Logger getLogger() {
		return(LOGGER);
	}
}
