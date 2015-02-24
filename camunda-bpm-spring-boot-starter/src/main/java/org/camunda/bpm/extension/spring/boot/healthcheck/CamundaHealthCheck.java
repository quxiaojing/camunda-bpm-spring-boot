package org.camunda.bpm.extension.spring.boot.healthcheck;

import org.camunda.bpm.engine.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CamundaHealthCheck extends AbstractHealthIndicator {

//  @Autowired
//  ManagementService managementService;

  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {
  }

}
