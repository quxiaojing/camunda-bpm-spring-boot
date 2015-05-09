package org.camunda.bpm.extension.spring.boot.rest;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SampleCamundaBootRestApplication {

  private static Log logger = LogFactory.getLog(SampleCamundaBootRestApplication.class);

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(SampleCamundaBootRestApplication.class)
      .run();
  }

}
