package org.camunda.bpm.extension.spring.boot.base;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.extension.spring.boot.AbstractCamundaSpringBootIT;
import org.camunda.bpm.extension.spring.boot.rest.CamundaJerseyConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by chris on 18.02.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleCamundaBootBaseApplication.class)
@DirtiesContext
public class CamundaSpringBootApplicationTest extends AbstractCamundaSpringBootIT {

  @Autowired
  ManagementService managementService;
  @Autowired
  RepositoryService repositoryService;

  @Autowired
  Environment environment;

  @Test
  public void processEngineAvailable() throws Exception {
    assertNotNull(processEngine);
    assertEquals(BpmPlatform.getProcessEngineService().getProcessEngine("default"), processEngine);
  }

  @Test
  public void processEngineConfiguration() {
    assertNotNull(processEngineConfiguration);
    assertNull(processEngineConfiguration.getJpaEntityManagerFactory());
    assertTrue(processEngineConfiguration.getTransactionManager() instanceof JpaTransactionManager);
  }

  @Test(expected = NoSuchBeanDefinitionException.class)
  public void noRestApiAvailable() {
    CamundaJerseyConfig camundaJerseyConfig = beanFactory.getBean(CamundaJerseyConfig.class);
  }

  @Test
  public void managementServiceAvailable() {
    assertNotNull(managementService);
  }

  // TODO: test for all services to be available

  @Test
  public void historyLevelIsFullByDefault() {
    assertEquals(HistoryLevel.HISTORY_LEVEL_FULL.getName(), processEngine.getProcessEngineConfiguration().getHistory());
  }

  @Test
  public void bpmnProcessIsDeploy() {
    assertEquals(1, repositoryService.createProcessDefinitionQuery().count());
  }

}
