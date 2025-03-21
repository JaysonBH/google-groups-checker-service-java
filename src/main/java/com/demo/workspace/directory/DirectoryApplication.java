package com.demo.workspace.directory;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.workspace.directory.Utils.ServiceAccountUtils;

@SpringBootApplication
public class DirectoryApplication {
	@Bean
	public ServiceAccountUtils serviceAccountUtils() throws IOException {
		return new ServiceAccountUtils();
	}

	public static void main(String[] args)  {
		SpringApplication.run(DirectoryApplication.class, args);
	}

}