package org.camunda.bpm.extension.spring.boot.base;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.camunda.bpm.extension.spring.boot.conf.CamundaJpaAutoConfiguration;
import org.camunda.bpm.extension.spring.boot.conf.CamundaRestApiAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by hawky4s on 10.02.15.
 */
@SpringBootApplication(exclude = {
  CamundaRestApiAutoConfiguration.class,
  CamundaJpaAutoConfiguration.class
})
public class SampleCamundaBootBaseApplication {

  private static Log logger = LogFactory.getLog(SampleCamundaBootBaseApplication.class);

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(SampleCamundaBootBaseApplication.class)
      .run();
  }

}
