package org.camunda.bpm.extension.spring.boot.web;

import org.camunda.bpm.extension.spring.boot.conf.CamundaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Needed for WAR-only deployment.
 */
public class CamundaSpringWebInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(CamundaAutoConfiguration.class);
  }

}
