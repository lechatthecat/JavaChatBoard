package com.blogspot.noteoneverything.chatboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ChatboardApplication extends SpringBootServletInitializer  {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ChatboardApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ChatboardApplication.class, args);
	}
}
