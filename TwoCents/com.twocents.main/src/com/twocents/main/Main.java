package com.twocents.main;

import org.apache.log4j.Logger;

import com.twocents.core.database.DatabaseSetUp;
import com.twocents.environment.Environment;
import com.twocents.ui.TwoCentsUI;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {

            DatabaseSetUp.startHsqlInServerMode();

            org.apache.log4j.Logger.getLogger("com.twocents").setLevel(
                    org.apache.log4j.Level.INFO);

            org.apache.log4j.Logger.getLogger("net.sf.hibernate").setLevel(
                    org.apache.log4j.Level.WARN);
            org.apache.log4j.Logger.getLogger("ar.com.fdvs.dj").setLevel(
                    org.apache.log4j.Level.ERROR);

            new TwoCentsUI(getVersion());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            System.exit(0);
        }
    }

    public static String getVersion(){
        String implementationVersion = Main.class.getPackage().getImplementationVersion();

        String jreVersion = System.getProperty("os.arch");
        String path = System.getProperty("user.dir");

        logger.info(path);
        logger.info("implementationVersion: " + implementationVersion);

        logger.info("JRE version: " + jreVersion);
        if(implementationVersion==null){
            return Environment.DEV_MODE;
        }
        else{
            return implementationVersion;
        }
    }


}
