package com.covalent;

import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//https://www.agilegroup.co.jp/technote/springboot-fileupload-error-handling.html
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringBootWebApplication {

	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(
				SpringBootWebApplication.class);
		Properties prop = new Properties();
		prop.setProperty("spring.resources.staticLocations",
				"classpath:/templates/");
		app.setDefaultProperties(prop);
		app.run(args);
		// SpringApplication.run(SpringBootWebApplication.class, args);
	}

//	// Tomcat large file upload connection reset
//	// http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
//	@Bean
//	public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
//
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//
//		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
//			if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
//				// -1 means unlimited
//				((AbstractHttp11Protocol<?>) connector.getProtocolHandler())
//						.setMaxSwallowSize(-1);
//			}
//		});
//
//		return tomcat;
//
//	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		System.out.println("asyncExecutor initialize");
		return executor;
	}

}