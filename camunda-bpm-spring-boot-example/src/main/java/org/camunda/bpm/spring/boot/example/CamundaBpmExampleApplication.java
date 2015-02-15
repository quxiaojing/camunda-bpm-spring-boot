package org.camunda.bpm.spring.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CamundaBpmExampleApplication extends SpringApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(CamundaBpmExampleApplication.class)
      .run();
  }

}
