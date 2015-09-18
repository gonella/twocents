package com.twocents.core.database;

import java.io.File;

import org.apache.log4j.Logger;
import org.hsqldb.Server;

import com.twocents.environment.Environment;

public class DatabaseSetUp {

	private static Logger logger = Logger.getLogger(DatabaseSetUp.class);

	public static String getVersion() {
		String implementationVersion = DatabaseSetUp.class.getPackage()
				.getImplementationVersion();
		if (implementationVersion == null) {
			return "DEV MODE";
		} else {
			return implementationVersion;
		}
	}

	public static void startHsqlInServerMode() throws Exception {

		boolean isStarted = false;
		int attempt = 0;

		do {

			try {
				Server dbServer = new Server();

				// Avoid too many log messages during HSQL start up
				// dbServer.setLogWriter(null);
				dbServer.setSilent(true);

				// Point to Twocent's DB's name. IMPORTANT: twc must be the
				// configure the same name in the datasource definition on
				// spring.
				dbServer.setDatabaseName(0, Environment.DATABASE_NAME);
				dbServer.setDatabasePath(0, "file:" + File.separator
						+ new File(".").getCanonicalPath() + File.separator
						+ Environment.DATABASE_DIR_FILENAME);
				dbServer.start();
				dbServer.checkRunning(true);
				isStarted = true;
			} catch (Exception e) {

				logger.info("Aplicação ja esta rodando");
				System.exit(0);
				// logger.info("Database could not be started. Waiting 10 seconds before new attempt. Attempt "
				// + (++attempt) + " of 3");
				// Thread.sleep(10000);
			}
		} while (!isStarted && attempt < 3);

		if (!isStarted) {
			throw new Exception(
					"Problema carregando o banco de dados, a aplicação será fechada.");
		}

	}
}
