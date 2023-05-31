package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication                             // this class acts like a configuration class / IOC doesn't have the details which obj to create, so we need to configure here
public class BlogApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args) ;
		}
		@Bean                                          // @Bean applied on a method when this is used when an external library Class obj we want to Create
		public ModelMapper modelMapper () {

			return new ModelMapper();
		}
	}



