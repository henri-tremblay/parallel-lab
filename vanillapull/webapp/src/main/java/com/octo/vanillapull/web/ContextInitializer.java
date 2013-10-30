package com.octo.vanillapull.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	public final static Logger logger = LoggerFactory.getLogger(ContextInitializer.class);
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		String implementation = System.getProperty("implementation");
		logger.info("\n-----------------------------------------------------------------");
		logger.info("\n\t\tAlgorithm implementation is :\"" + implementation + "\"");
		logger.info("\n-----------------------------------------------------------------");
		applicationContext.getEnvironment().setActiveProfiles(implementation);
	}

	

}
