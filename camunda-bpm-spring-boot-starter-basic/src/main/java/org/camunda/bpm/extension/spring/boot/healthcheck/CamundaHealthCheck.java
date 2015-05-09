package org.camunda.bpm.extension.spring.boot.healthcheck;

import org.camunda.bpm.engine.ManagementService;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class CamundaHealthCheck extends AbstractHealthIndicator {

  @Inject
  private ManagementService managementService;

  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {
  }

}
