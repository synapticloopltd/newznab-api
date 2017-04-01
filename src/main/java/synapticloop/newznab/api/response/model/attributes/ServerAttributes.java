package synapticloop.newznab.api.response.model.attributes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.newznab.api.response.BaseModel;

public class ServerAttributes extends BaseModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerAttributes.class);

	@JsonProperty("appversion")  private String appVersion;
	@JsonProperty("version")  private Float version;
	@JsonProperty("title")  private String title;
	@JsonProperty("strapline")  private String strapline;
	@JsonProperty("email")  private String email;
	@JsonProperty("url")  private String url;
	@JsonProperty("image")  private String image;

	public String getAppVersion() { return appVersion; }
	public Float getVersion() { return version; }
	public String getTitle() { return title; }
	public String getStrapline() { return strapline; }
	public String getEmail() { return email; }
	public String getUrl() { return url; }
	public String getImage() { return image; }

	@Override
	public Logger getLogger() { return(LOGGER); }

}
