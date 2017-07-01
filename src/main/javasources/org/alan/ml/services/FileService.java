package org.alan.ml.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.alan.ml.domain.Data;
import org.alan.ml.domain.DataKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileService {

	final static Logger logger = LogManager.getLogger(FileService.class);

	public static final String[] dataHeader = { "date", "website", "visits" };

	public static final String fileDelimiter = ConfigService.getConfig().getString("data.file.delimiter");

	public static final SimpleDateFormat dataFileDateFormat = new SimpleDateFormat(
			ConfigService.getConfig().getString("data.file.dateFormat"));

	public static List<Data> readDataFile(String filePath) {
		ArrayList<Data> datalist = new ArrayList<Data>();
		File file = new File(filePath);
		if (file.exists() && !file.isDirectory()) {
			logger.info("Start read data file: " + filePath);
			try (Stream<String> streamLines = Files.lines(Paths.get(filePath))) {
				streamLines.forEach(line -> {
					Data convertedData = convertLineToData(line);
					if (convertedData != null) {
						datalist.add(convertedData);
					}

				});
			} catch (IOException ex) {
				logger.error("Fail to read DataFile: " + ex);
			}
		}
		return datalist;
	}

	public static Data convertLineToData(String line) {

		logger.info("Import Data:" + line);
		String[] rawDataList = line.split(Pattern.quote(fileDelimiter));
		logArray(rawDataList, Level.DEBUG);
		// logArray(dataHeader,Level.DEBUG);
		if (Arrays.equals(rawDataList, dataHeader)) {
			logger.info("Is file header, skip");
			return null;
		} else {
			logger.info("Is file data, convert to Data Object");
			Data readedData = new Data();
			try {
				DataKey readedDataKey = new DataKey(dataFileDateFormat.parse(rawDataList[0]), rawDataList[1]);
				readedData.setId(readedDataKey);
				readedData.setVisits(Integer.parseInt(rawDataList[2]));
				return readedData;
			} catch (Exception ex) {
				logger.error("Fail to convert data to Data Object: " + ex);
				return null;
			}
		}
	}

	public static void logArray(String[] array, Level level) {
		List<String> arrayList = Arrays.asList(array);
		logger.log(level, arrayList.toString());
	}
}
