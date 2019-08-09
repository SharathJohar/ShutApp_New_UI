/**
 * 
 */
package com.shutapp.newui.loginflow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author SHARATH
 *
 * 
 */
public class Log4j {

	/**
	 * @param args
	 */
	
	static Logger log =  LogManager.getLogger(Log4j.class);
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.trace("This is trace");
		log.info("This is info");
		log.error("this is error");
		log.warn("this is warn");
		log.fatal("this is fatal");



	}

}
