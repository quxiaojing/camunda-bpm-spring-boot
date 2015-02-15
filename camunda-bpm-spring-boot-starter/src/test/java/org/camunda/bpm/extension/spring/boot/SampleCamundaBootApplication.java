package org.camunda.bpm.extension.spring.boot;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by hawky4s on 10.02.15.
 */
@SpringBootApplication
public class SampleCamundaBootApplication {

  private static Log logger = LogFactory.getLog(SampleCamundaBootApplication.class);

  @Bean
  protected ServletContextListener listener() {
    return new ServletContextListener() {
      @Override
      public void contextInitialized(ServletContextEvent sce) {
        logger.info("ServletContext initialized");
      }
      @Override
      public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ServletContext destroyed");
      }
    };
  }
  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(SampleCamundaBootApplication.class)
      .run();
  }

}
