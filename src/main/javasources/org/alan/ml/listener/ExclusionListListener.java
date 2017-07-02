package org.alan.ml.listener;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.alan.ml.domain.ExclusionSite;
import org.alan.ml.services.ConfigService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

@WebListener
public class ExclusionListListener implements ServletContextListener {

	static final Logger logger = LogManager.getLogger(ExclusionListListener.class);

	private volatile boolean active = true;

	private static final int exclusionListCheckPeriod = ConfigService.getConfig()
			.getInt("api.exclusionList.checkPeriod");

	private static final String exclusionListAPIUrl = ConfigService.getConfig().getString("api.exclusionList.apiurl");

	private static final int defaultExclusionListCheckPeriod = 120000;

	Runnable exclusionListDeamon = new Runnable() {

		public void run() {
			while (active) {
				InputStream inputStream = null;
				try {
					logger.info("Get Exclusion List from API...");

					inputStream = new URL(exclusionListAPIUrl).openStream();
					String exclusionList = IOUtils.toString(inputStream, Charset.forName("UTF-8"));

					JSONArray exclusionListJson = new JSONArray(exclusionList);
					logger.info("exclusionListJson: " + exclusionListJson.toString());
					
					ExclusionSite.setExclusionMap(exclusionListJson);
					logger.info("ExclusionSiteMap: " + ExclusionSite.getExclusionMap().toString());

					if (exclusionListCheckPeriod != 0) {
						Thread.sleep(exclusionListCheckPeriod);
					} else {
						Thread.sleep(defaultExclusionListCheckPeriod);
					}

				} catch (Exception e) {
					logger.error("exclusionListDeamon error: " + e);
					try {
						Thread.sleep(defaultExclusionListCheckPeriod);
					} catch (Exception ex) {
						logger.error("exclusionListDeamon error cannot sleep: " + ex);
						active = false;
					}
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
			}
		}
	};

	public void contextInitialized(ServletContextEvent event) {
		new Thread(exclusionListDeamon).start();
	}

	public void contextDestroyed(ServletContextEvent event) {
		active = false;
	}

}
