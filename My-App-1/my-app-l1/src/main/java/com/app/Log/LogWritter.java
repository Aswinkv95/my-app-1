package com.app.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogWritter {
	
	private static Logger log = LoggerFactory.getLogger(LogWritter.class);
	
	public void logInfo(String message) {
		log.info(message);
	}
	public void logError(String message, Exception e) {
		log.error(message, e);
    }
	public void logDebug(String message) {
		log.debug(message);
    }

}
