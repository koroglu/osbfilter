package com.oracle.acs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@ComponentScan("com.oracle.acs")
@EnableWebMvc
public class Application {

	private static String[] beanDefinitionNames;

	public static void main(String[] args) {
		System.out.println("######## STARTING......");
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		
		for (String string : args) {
			System.out.println("Bean Initialized  " + string );
		}
		System.out.println("######## FINISHED...");
	}
}
