package org.camunda.bpm.extension.spring.boot;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractCamundaSpringBootIT extends CamundaConfigurationClassTests {

  @Autowired
  protected BeanFactory beanFactory;
  @Autowired
  protected SpringProcessEngineConfiguration processEngineConfiguration;
  @Autowired
  protected ProcessEngine processEngine;

}
