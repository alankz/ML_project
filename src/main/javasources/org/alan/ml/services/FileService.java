package org.alan.ml.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.alan.ml.domain.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileService {
	
	final static Logger logger = LogManager.getLogger(FileService.class);
	
	public static final String[] dataHeader = {"date","website","visits"};
	
	public static List<Data> readDataFile (String filePath){
		ArrayList<Data> datalist = new ArrayList<Data>();
		File file = new File(filePath);
		if (file.exists() && !file.isDirectory()) {
				logger.info("Start read data file: " + filePath);
			try (Stream<String> streamLines = Files.lines(Paths.get(filePath))) {
				streamLines.forEach(line -> convertLineToData(line));
			} catch (IOException ex) {
				logger.error("Fail to read DataFile" + ex);
			}
		}
		return datalist;
	}
	
	public static Data convertLineToData (String line){
		logger.info("Import Data:" + line);
		return new Data();
	}

}
