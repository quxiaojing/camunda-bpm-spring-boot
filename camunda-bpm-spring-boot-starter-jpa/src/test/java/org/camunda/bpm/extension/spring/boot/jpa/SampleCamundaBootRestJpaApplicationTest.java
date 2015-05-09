package org.camunda.bpm.extension.spring.boot.jpa;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleCamundaBootRestJpaApplication.class)
@DirtiesContext
public class SampleCamundaBootRestJpaApplicationTest {

  @Autowired
  protected BeanFactory beanFactory;
  @Autowired
  protected SpringProcessEngineConfiguration processEngineConfiguration;
  @Autowired
  protected ProcessEngine processEngine;

  @Test
  public void processEngineConfiguration() {
    assertNotNull(processEngineConfiguration);
    assertNull(processEngineConfiguration.getJpaEntityManagerFactory());
    assertTrue(processEngineConfiguration.getTransactionManager() instanceof JpaTransactionManager);
  }

}
