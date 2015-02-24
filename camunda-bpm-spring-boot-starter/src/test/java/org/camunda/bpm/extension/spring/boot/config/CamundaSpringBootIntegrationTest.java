package org.camunda.bpm.extension.spring.boot.config;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.spring.container.ManagedProcessEngineFactoryBean;
import org.camunda.bpm.extension.spring.boot.SampleCamundaBootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by chris on 18.02.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleCamundaBootApplication.class)
@WebIntegrationTest(randomPort = true)
@DirtiesContext
public class CamundaSpringBootIntegrationTest {

  @Autowired
  ProcessEngine processEngine;

  @Autowired
  ManagementService managementService;

  @Test
  public void processEngineAvailable() throws Exception {
    assertNotNull(processEngine);
    assertEquals(BpmPlatform.getDefaultProcessEngine(), processEngine);
  }

  @Test
  public void managementServiceAvailable() {
    assertNotNull(managementService);
  }

  @Test
  public void historyLevelIsFullByDefault() {
    assertEquals(HistoryLevel.HISTORY_LEVEL_FULL.getName(), processEngine.getProcessEngineConfiguration().getHistory());
  }

}
