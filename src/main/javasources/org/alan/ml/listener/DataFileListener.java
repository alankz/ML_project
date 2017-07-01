package org.alan.ml.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.alan.ml.services.ConfigService;
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

	Runnable dataFileDeamon = new Runnable() {

		public void run() {
			while (active) {
				try {
					logger.info("Checking data file...");
					String filePath = ConfigService.getConfig().getString("data.file.path");
					if (checkDataFileModified(filePath)) {
						FileService.readDataFile(filePath);
					}

					if (dataFileCheckPeriod != 0) {
						Thread.sleep(dataFileCheckPeriod);
					} else {
						Thread.sleep(defaultDataFileCheckPeriod);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
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
