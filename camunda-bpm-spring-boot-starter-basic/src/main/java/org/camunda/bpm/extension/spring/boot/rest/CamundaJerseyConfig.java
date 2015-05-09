package org.camunda.bpm.extension.spring.boot.rest;

import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.logging.Logger;

@ApplicationPath("/api")
public class CamundaJerseyConfig extends ResourceConfig {

  protected Logger logger = Logger.getLogger(this.getClass().getName());

  public CamundaJerseyConfig() {
    super();

    logger.info("Configuring camunda rest api.");

    registerClasses(CamundaRestResources.getResourceClasses());
    registerClasses(CamundaRestResources.getConfigurationClasses());
    register(JacksonFeature.class);

    logger.info("Finished configuring camunda rest api.");
  }
}
