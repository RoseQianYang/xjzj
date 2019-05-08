package com.yunqi.jhf.web;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class AppContextListener implements ServletContextListener {	
	
	private Logger log = Logger.getLogger(AppContextListener.class);

	public void contextInitialized(ServletContextEvent sce) {

		try {
			log.info("");
			log.info("|======== jhf-app-server AppContext is initialized. ========|");
			log.info("|");
			log.info("|");
			log.info("|======== jhf-app-server AppContext is finished.    ========|");
			log.info("");

		} catch (Exception e) {
			log.fatal("fatal error when app startup listen", e);
		}		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		log.info("jg-adm ServletContext is destroyed.");
	}

}