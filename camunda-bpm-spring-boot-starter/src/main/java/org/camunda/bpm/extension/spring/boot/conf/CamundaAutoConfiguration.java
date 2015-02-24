package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor;
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

  @Autowired
  ProcessEngine processEngine;

  @Override
  @Bean
  public SpringProcessEngineConfiguration createProcessEngineConfiguration(DataSource dataSource,
                                                                           PlatformTransactionManager transactionManager,
                                                                           SpringJobExecutor springJobExecutor) throws IOException {
    SpringProcessEngineConfiguration config = super.createProcessEngineConfiguration(dataSource, transactionManager, springJobExecutor);

    return config;
  }

}
