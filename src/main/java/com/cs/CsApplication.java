package com.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.cs")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.cs")
public class CsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CsApplication.class, args);
	}

}
