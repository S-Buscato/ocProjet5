package com.SafetyNet.Alert.Util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PropertiesFileLog4j {

    static Logger logger = Logger.getLogger(PropertiesFileLog4j.class);

    public String myLog(String type){
        String log4jConfigFile = System.getProperty("resources.dir") + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);

        /*switch (type){
            case LogType.DEBUG:
                logger.debug("mon debug");
                break;
            case LogType.INFO:
                logger.info("mon info");
                break;
            case LogType.WARNING:
                logger.warn("mon warning");
                break;
            case LogType.ERROR:
                logger.info("mon erreur");
                break;
            default:break;
        }*/
        return null;
    }
}
