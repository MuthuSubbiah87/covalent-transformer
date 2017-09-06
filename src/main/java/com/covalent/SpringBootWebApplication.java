package com.covalent;


import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@SpringBootApplication
@EnableAsync
public class SpringBootWebApplication {
	
	final static Logger logger = Logger.getLogger(SpringBootWebApplication.class);

	public static void main(String[] args) throws Exception {

		 SpringApplication.run(SpringBootWebApplication.class, args);
	}

 	@Bean
 	public Executor asyncExecutor() {
 		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
 		executor.setCorePoolSize(2);
 		executor.setMaxPoolSize(2);
 		executor.setQueueCapacity(500);
 		executor.setThreadNamePrefix("AsynFileProcessThread");
 		executor.initialize();
 		logger.debug("AsynFileProcessThread Initialized");
 		return executor;
 	}

}