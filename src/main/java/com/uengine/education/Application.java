package com.uengine.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



	@Bean
	public ResourceProcessor<Resource<Dashboard>> personProcessor() {

		return new ResourceProcessor<Resource<Dashboard>>() {

			@Override
			public Resource<Dashboard> process(Resource<Dashboard> resource) {

				resource.add(new Link("http://localhost:8080/customers/" + resource.getContent().getUserId(), "customer"));
				return resource;
			}
		};
	}
}
