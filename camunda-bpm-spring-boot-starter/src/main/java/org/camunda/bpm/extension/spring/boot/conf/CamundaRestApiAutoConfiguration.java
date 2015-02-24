package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;
import javax.ws.rs.ApplicationPath;

/**
 * Created by hawky4s on 07.02.15.
 */
@Configuration
@ConditionalOnClass(org.camunda.bpm.engine.rest.impl.CamundaRestResources.class)
@ConditionalOnWebApplication
public class CamundaRestApiAutoConfiguration extends AbstractCamundaAutoConfiguration {

  @Named
  @ApplicationPath("api")
  public static class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
      this.registerClasses(CamundaRestResources.getResourceClasses());
      this.registerClasses(CamundaRestResources.getConfigurationClasses());
//      this.register(GreetingEndpoint.class);
      this.register(JacksonFeature.class);
    }
  }

}
