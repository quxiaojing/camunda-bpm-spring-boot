package org.camunda.bpm.extension.spring.boot.jpa;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SampleCamundaBootRestJpaApplication {

  private static Log logger = LogFactory.getLog(SampleCamundaBootRestJpaApplication.class);

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(SampleCamundaBootRestJpaApplication.class)
      .run();
  }

}
