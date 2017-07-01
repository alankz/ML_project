package org.alan.ml.services;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigService {
	final static Logger logger = LogManager.getLogger(ConfigService.class);

    private static final Configuration mlConfig = buildConfiguration();

    private static Configuration buildConfiguration() {
    	Configurations configs = new Configurations();
        try {
        	logger.info("Start buildConfiguration");
        	Configuration config = configs.properties(new File("mlConfig.properties"));
            return config;
        }
        catch (Throwable ex) {
        	logger.error("Failed to initial mlConfig: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Configuration getConfig() {
   	  return mlConfig;
    }

    public static void refreshConfig() {
    	logger.info("Refresh mlConfig");
    	buildConfiguration();
    }
}
