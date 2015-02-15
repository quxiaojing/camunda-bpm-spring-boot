package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.jobexecutor.CallerRunsRejectedJobsHandler;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor;
import org.camunda.bpm.extension.spring.boot.CamundaProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by hawky4s on 09.02.15.
 */
@EnableConfigurationProperties(CamundaProperties.class)
public abstract class AbstractCamundaAutoConfiguration {

  @Autowired
  protected BeanFactory beanFactory;
  @Autowired
  protected ApplicationContext appContext;
  @Autowired
  protected CamundaProperties camundaProperties;

  protected ProcessEngine processEngine;

  public SpringProcessEngineConfiguration createProcessEngineConfiguration(DataSource dataSource,
                                                                           PlatformTransactionManager transactionManager,
                                                                           SpringJobExecutor springJobExecutor) throws IOException {
    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
    config.setDataSource(dataSource);
//    config.setProcessEngineName("processEngine");

//    config.setDatabaseTablePrefix("");
//    config.setDatabaseSchemaUpdate(Boolean.TRUE.toString());

//    config.setJobExecutorDeploymentAware(camundaSettings.isJobExecutorDeploymentAware());
    if (springJobExecutor != null) {
      config.setJobExecutor(springJobExecutor);
      config.setJobExecutorActivate(true);
    }
//    config.setJobExecutorActivate(false);

//    config.setCmmnEnabled(camundaSettings.isCmmnEnabled());

//    config.setHistory(HistoryLevel.HISTORY_LEVEL_FULL.getName());

    config.setTransactionManager(transactionManager);
//    config.setDeploymentResources(appContext.getResources("classpath*:*.bpmn"));

//    config.setJpaEntityManagerFactory(null);
    return config;
  }

  @Bean
  public SpringJobExecutor springJobExecutor(TaskExecutor taskExecutor) {
    SpringJobExecutor springJobExecutor = new SpringJobExecutor();
    springJobExecutor.setTaskExecutor(taskExecutor);
    springJobExecutor.setRejectedJobsHandler(new CallerRunsRejectedJobsHandler());
    return springJobExecutor;
  }

  @Bean
  public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration configuration) throws IOException {
    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(configuration);
    return processEngineFactoryBean;
  }

  @Bean
  public ProcessEngine processEngine(ProcessEngineFactoryBean processEngineFactoryBean) throws Exception {
    return processEngineFactoryBean.getObject();
  }

  @Bean
  public RepositoryService repositoryService() throws Exception {
    return processEngine.getRepositoryService();
  }

  @Bean
  public RuntimeService runtimeService() throws Exception {
    return processEngine.getRuntimeService();
  }

  @Bean
  public TaskService taskService() throws Exception {
    return processEngine.getTaskService();
  }

  @Bean
  public HistoryService historyService() throws Exception {
    return processEngine.getHistoryService();
  }

  @Bean
  public ManagementService managementService() throws Exception {
    return processEngine.getManagementService();
  }

  @Bean
  public CaseService caseService() throws Exception {
    return processEngine.getCaseService();
  }

  @Bean
  public FilterService filterService() throws Exception {
    return processEngine.getFilterService();
  }

  @Bean
  public FormService formService() throws Exception {
    return processEngine.getFormService();
  }

  @Bean
  public IdentityService identityService() throws Exception {
    return processEngine.getIdentityService();
  }

  @Bean
  @ConditionalOnMissingBean
  public TaskExecutor taskExecutor() {
    return new SimpleAsyncTaskExecutor();
  }

}
