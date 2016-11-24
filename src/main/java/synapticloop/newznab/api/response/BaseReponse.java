package synapticloop.newznab.api.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import synapticloop.newznab.api.exception.NewzNabApiException;

public abstract class BaseReponse {
	protected final JSONObject response;

	protected BaseReponse(String json) throws NewzNabApiException {
		this(parse(json));
	}

	public BaseReponse(JSONObject jsonObject) {
		this.response = jsonObject;
	}

	/**
	 * Parse a string into a JSON object
	 *
	 * @param json the data to parse to an object
	 * @return the parsed JSON object
	 * @throws NewzNabApiException if there was an error parsing the object
	 */
	private static JSONObject parse(String json) throws NewzNabApiException {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException ex) {
			throw new NewzNabApiException(json, ex);
		}
		return jsonObject;
	}
	/**
	 * Read and remove String with key from JSON
	 * 
	 * @param key the key to read as a string and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected String readString(String key) {
		return this.readString(response, key);
	}

	/**
	 * Read and remove String with key from JSON object
	 * 
	 * @param response The JSON object to read from
	 * @param key the key to read as a string and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected String readString(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value.toString();
	}

	/**
	 * Read and remove int with key from JSON object
	 * 
	 * @param key the key to read as an int and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected Integer readInt(String key) {
		return(readInt(response, key));
	}

	protected Integer readInt(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof Number ? ((Number) value).intValue() : Integer.parseInt(value.toString());
	}

	/**
	 * Read and remove long with key from JSON object
	 * 
	 * @param key the key to read as a long and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected Long readLong(String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof Number ? ((Number) value).longValue() : Long.parseLong(value.toString());
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 * 
	 * @param key the key to read as a JSONObject and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONObject readObject(String key) {
		return this.readObject(response, key);
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 * 
	 * @param response The JSON object to read from
	 * @param key the key to read as a JSONObject and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONObject readObject(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof JSONObject ? (JSONObject) value : null;
	}

	/**
	 * Read and remove JSONArray with key from JSON object
	 * 
	 * @param key the key to read as a JSONArray and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONArray readObjects(String key) {
		return(readObjects(response, key));
	}

	protected JSONArray readObjects(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof JSONArray ? (JSONArray) value : null;
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 *
	 * @param key the key to read as a JSONObject and put keys and values into map
	 *
	 * @return the read keys and values (or null if it doesn't exist)
	 */
	protected Map<String, String> readMap(String key) {
		final Map<String, String> map = new HashMap<String, String>();
		JSONObject value = this.readObject(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		for (String k:  value.keySet().toArray(new String[value.keySet().size()])) {
			map.put(k, this.readString(value, k));
		}
		return map;
	}

	/**
	 * Parse through the expected keys to determine whether any of the keys in
	 * the response will not be mapped.  This will loop through the JSON object
	 * and any key left in the object will generate a 'WARN' message.  The
	 * response class __MUST__ remove the object (i.e. jsonObject.remove(KEY_NAME))
	 * after getting the value, or use the utility methods in this class.  This 
	 * is used more as a testing tool/sanity test than anything else as there 
	 * are some instances in where keys are returned, however are not listed in 
	 * the documentation.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	protected void warnOnMissedKeys() {
		if (getLogger().isWarnEnabled()) {
			Iterator keys = response.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				getLogger().warn("Found an unexpected key of '{}' in JSON that is not mapped to a field, with value '{}'.", key, response.get(key));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected void warnOnMissedKeys(JSONObject jsonObject) {
		if (getLogger().isWarnEnabled()) {
			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				getLogger().warn("Found an unexpected key of '{}' in JSON that is not mapped to a field, with value '{}'.", key, jsonObject.get(key));
			}
		}
	}

	/**
	 * Get the logger to use for Logging from the base class
	 * 
	 * @return The logger to use
	 */
	protected abstract Logger getLogger();}
