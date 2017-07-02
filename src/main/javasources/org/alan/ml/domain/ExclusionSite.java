package org.alan.ml.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.alan.ml.listener.DataFileListener;
import org.alan.ml.services.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExclusionSite {

	static final Logger logger = LogManager.getLogger(ExclusionSite.class);

	private static Map<String, ExclusionSite> exclusionMap = new ConcurrentHashMap<String, ExclusionSite>();

	public static final SimpleDateFormat exclusionListDateFormat = new SimpleDateFormat(
			ConfigService.getConfig().getString("api.exclusionList.dateFormat"));

	public static final String hostApiKey = "host";
	public static final String excludedSinceApiKey = "excludedSince";
	public static final String excludedTillApiKey = "excludedTill";

	private String host;
	private Date excludedSince;
	private Date excludedTill;

	public ExclusionSite() {
	}

	public ExclusionSite(String host, Date excludedSince, Date excludedTill) {
		super();
		this.host = host;
		this.excludedSince = excludedSince;
		this.excludedTill = excludedTill;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getExcludedSince() {
		return excludedSince;
	}

	public void setExcludedSince(Date excludedSince) {
		this.excludedSince = excludedSince;
	}

	public Date getExcludedTill() {
		return excludedTill;
	}

	public void setExcludedTill(Date excludedTill) {
		this.excludedTill = excludedTill;
	}

	@Override
	public String toString() {
		return "ExclusionSite [host=" + host + ", excludedSince=" + excludedSince + ", excludedTill=" + excludedTill
				+ "]";
	}

	// if date within the range of exclusedPeriod return false
	public boolean validateDate(Date date) {
		if (excludedSince != null) {
			if (excludedTill==null) {
				return !date.after(excludedSince);
			} else  {
				return !(date.after(excludedSince) && date.before(excludedTill));
			}
		} else {
			return true;
		}
	}

	public static ExclusionSite parseJsonObject(JSONObject obj) {
		ExclusionSite result = new ExclusionSite();

		if (obj.has(hostApiKey)) {
			result.setHost(obj.getString(hostApiKey));
		} else {
			return null;
		}

		if (obj.has(excludedSinceApiKey)) {
			try {
				result.setExcludedSince(exclusionListDateFormat.parse(obj.getString(excludedSinceApiKey)));
			} catch (Exception ex) {
				logger.error("ExclusionSite Cannot parse Host:" + result.getHost() + " String: "
						+ obj.getString(excludedSinceApiKey));
			}
		}

		if (obj.has(excludedTillApiKey)) {
			try {
				result.setExcludedTill(exclusionListDateFormat.parse(obj.getString(excludedTillApiKey)));
			} catch (Exception ex) {
				logger.error("ExclusionSite Cannot parse Host:" + result.getHost() + " String: "
						+ obj.getString(excludedTillApiKey));
			}
		}

		return result;
	}

	public static Map<String, ExclusionSite> getExclusionMap() {
		return exclusionMap;
	}

	public static void setExclusionMap(JSONArray jsonArray) {
		ConcurrentHashMap<String, ExclusionSite> tempMap = new ConcurrentHashMap<String, ExclusionSite>();
		jsonArray.forEach(item -> {
			JSONObject obj = (JSONObject) item;
			ExclusionSite tempSite = parseJsonObject(obj);
			tempMap.put(tempSite.getHost(), tempSite);
		});
		exclusionMap = tempMap;
	}

}
