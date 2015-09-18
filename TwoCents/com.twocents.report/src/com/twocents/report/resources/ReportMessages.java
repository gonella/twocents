package com.twocents.report.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ReportMessages {

	private static Logger logger = Logger.getLogger(ReportMessages.class);

	private static String REPORT_MESSAGES = "com/twocents/report/resources/TwoCentsReport";

	private static String UNKNOWN = "UNKNOWN";

	private static ResourceBundle resource = null;

	/**
	 * @return the props
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static ResourceBundle getBundle() {

		if (resource == null) {
			resource = ResourceBundle.getBundle(REPORT_MESSAGES);
		}
		return resource;
	}

	public static String getMessage(Integer errorCode) {
		return getMessage(errorCode.toString());
	}

	public static String getMessage(String key) {

		try {
			return getBundle().getString(key);

		} catch (NullPointerException e) {
			logger.error(e);
		}

		return UNKNOWN;
	}

}
