package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Named;

/**
 * Created by hawky4s on 07.02.15.
 */
//@Configuration
public class CamundaRestAutoConfiguration extends AbstractCamundaAutoConfiguration {

  @Named
  public static class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
      this.registerClasses(CamundaRestResources.getResourceClasses());
      this.registerClasses(CamundaRestResources.getConfigurationClasses());
//      this.register(GreetingEndpoint.class);
//      this.register(JacksonFeature.class);
    }
  }

}
