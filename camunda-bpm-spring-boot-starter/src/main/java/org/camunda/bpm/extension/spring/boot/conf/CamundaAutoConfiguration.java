package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ConditionalOnMissingBean(ProcessEngineFactoryBean.class)
public class CamundaAutoConfiguration extends AbstractCamundaAutoConfiguration {



  @Bean
  public SpringProcessEngineConfiguration springProcessEngineConfig() throws IOException {
    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
    config.setDataSource(beanFactory.getBean(DataSource.class));
    config.setProcessEngineName("processEngine");

    config.setDatabaseSchemaUpdate(Boolean.TRUE.toString());

    config.setJobExecutorDeploymentAware(camundaProperties.isJobExecutorDeploymentAware());
    config.setJobExecutorActivate(true);

    config.setCmmnEnabled(camundaProperties.isCmmnEnabled());

    config.setHistory(HistoryLevel.HISTORY_LEVEL_FULL.getName());

    config.setTransactionManager(beanFactory.getBean(PlatformTransactionManager.class));
    config.setDeploymentResources(appContext.getResources("classpath*:*.bpmn"));

    return config;
  }


}
