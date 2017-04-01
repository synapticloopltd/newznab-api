package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import synapticloop.newznab.api.response.BaseModel;

public class RegistrationAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationAttributes.class);

	private static final String YES = "yes";

	@JsonProperty("available")  private boolean isAvailable;
	@JsonProperty("open")  private boolean isOpen;

	@JsonSetter
	private void setAvailable(String value) {
		this.isAvailable = YES.equals(value);
	}

	@JsonSetter
	private void setOpen(String value) {
		this.isOpen = YES.equals(value);
	}

	public boolean getIsAvailable() { return isAvailable; }
	public boolean getIsOpen() { return isOpen; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
