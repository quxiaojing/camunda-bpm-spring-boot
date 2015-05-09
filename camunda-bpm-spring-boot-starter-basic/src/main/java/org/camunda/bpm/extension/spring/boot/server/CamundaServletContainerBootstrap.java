package org.camunda.bpm.extension.spring.boot.server;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by hawky4s on 20.04.15.
 *
 * Bootstrap the Camunda BPM platform HERE?
 */
public class CamundaServletContainerBootstrap implements EmbeddedServletContainerCustomizer, ServletContextInitializer {

  public void customize(ConfigurableEmbeddedServletContainer container) {
    container.addInitializers(this);
  }

  public void onStartup(ServletContext servletContext) throws ServletException {
    System.out.println("Bootstrapping Camunda BPM platform ...");
  }
}
