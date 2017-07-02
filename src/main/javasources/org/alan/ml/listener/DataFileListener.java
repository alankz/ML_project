package org.alan.ml.listener;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.alan.ml.domain.Data;
import org.alan.ml.services.ConfigService;
import org.alan.ml.services.DataService;
import org.alan.ml.services.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class DataFileListener implements ServletContextListener {

	static final Logger logger = LogManager.getLogger(DataFileListener.class);

	private volatile boolean active = true;

	private volatile long fileTimeStamp = 0;

	private static final int dataFileCheckPeriod = ConfigService.getConfig().getInt("data.file.checkPeriod");

	private static final int defaultDataFileCheckPeriod = 120000;
	
	private static final DataService dataService = new DataService();

	Runnable dataFileDeamon = new Runnable() {

		public void run() {
			while (active) {
				try {
					logger.info("Checking data file...");
					String filePath = ConfigService.getConfig().getString("data.file.path");
					if (checkDataFileModified(filePath)) {
						List<Data> dataList = FileService.readDataFile(filePath);
						try {
							dataService.clearDataTable();
							dataService.importDataList(dataList);
						} catch (Exception ex) {
							logger.error("Fail to import data list: " + ex);
						}
					}

					if (dataFileCheckPeriod != 0) {
						Thread.sleep(dataFileCheckPeriod);
					} else {
						Thread.sleep(defaultDataFileCheckPeriod);
					}

				} catch (Exception e) {
					logger.error("DataFileDeamon error: " + e);
					try {
						Thread.sleep(defaultDataFileCheckPeriod);
					} catch (Exception ex) {
						logger.error("DataFileDeamon error cannot sleep: " + ex);
						active = false;
					}
				}
			}
		}
	};

	private boolean checkDataFileModified(String filePath) {
		File file = new File(filePath);
		if (file.exists() && !file.isDirectory()) {
			long timeStamp = file.lastModified();
			boolean result = this.fileTimeStamp != timeStamp;
			this.fileTimeStamp = timeStamp;
			return result;
		} else {
			return false;
		}

	}

	public void contextInitialized(ServletContextEvent event) {
		new Thread(dataFileDeamon).start();
	}

	public void contextDestroyed(ServletContextEvent event) {
		active = false;
	}

}
