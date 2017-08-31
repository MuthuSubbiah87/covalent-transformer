package com.covalent;


import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@SpringBootApplication
@EnableAsync
public class SpringBootWebApplication {

	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

	public static void main(String[] args) throws Exception {
//		SpringApplication app = new SpringApplication(
//				SpringBootWebApplication.class);
//		Properties prop = new Properties();
//		prop.setProperty("spring.resources.staticLocations",
//				"classpath:/templates/");
//		app.setDefaultProperties(prop);
//		app.run(args);
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
 		System.out.println("AsynFileProcessThread Init");
 		return executor;
 	}

}