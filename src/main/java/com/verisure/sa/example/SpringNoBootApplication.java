package com.verisure.sa.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.verisure.sa.example.exception.NotFoundException;
import com.verisure.sa.example.service.DeviceConfigService;

@Configuration
@ComponentScan("com.verisure.sa.example")
public class SpringNoBootApplication {
	
	private final static Logger log = LoggerFactory.getLogger(SpringNoBootApplication.class);

	public static void main(String[] args) {
		// Initialize Spring Context in a No SpringBoot application
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringNoBootApplication.class);
		String installationId = "ES1502494";
		String family = "Keyboard";
		DeviceConfigService service = ctx.getBean("deviceConfigServiceImpl", DeviceConfigService.class);  
		for (int i = 0; i < 1; i++) {
			try {
				String zone = service.getInstallationNextZoneId(installationId, family);
				log.info("Retrieved zone:" + zone);
			} catch (NotFoundException e) {
				log.error("Installation {} and family {} NOT FOUND", installationId, family, e);				
			} catch (Exception e) {
				log.error("Internal error.", installationId, family, e);			}
		}
	}
}
