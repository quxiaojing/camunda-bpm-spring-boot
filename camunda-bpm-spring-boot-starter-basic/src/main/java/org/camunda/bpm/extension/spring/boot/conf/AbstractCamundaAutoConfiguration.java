package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.jobexecutor.CallerRunsRejectedJobsHandler;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor;
import org.camunda.bpm.engine.spring.container.ManagedProcessEngineFactoryBean;
import org.camunda.bpm.extension.spring.boot.CamundaProperties;
import org.camunda.bpm.extension.spring.boot.SpringBootProcessApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@EnableConfigurationProperties(CamundaProperties.class)
public abstract class AbstractCamundaAutoConfiguration {

  protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

  @Autowired
  protected ApplicationContext appContext;
  @Autowired
  protected CamundaProperties camundaProperties;

  public SpringProcessEngineConfiguration createProcessEngineConfiguration(DataSource dataSource,
                                                                           PlatformTransactionManager transactionManager,
                                                                           SpringJobExecutor springJobExecutor) throws IOException {
    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
    config.setDataSource(new TransactionAwareDataSourceProxy(dataSource));
    config.setTransactionManager(transactionManager);
    if (springJobExecutor != null) {
      springJobExecutor

      config.setJobExecutor(springJobExecutor);
      config.setJobExecutorActivate(true);
    }
    config.setDatabaseSchemaUpdate(camundaProperties.getDatabaseSchemaUpdate());

    camundaProperties.apply(config);

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
    // we register the process engine in the platform
    ProcessEngineFactoryBean processEngineFactoryBean = new ManagedProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration(configuration);
    return processEngineFactoryBean;
  }

  @Bean
//  @ConditionalOnMissingBean
  public TaskExecutor taskExecutor() {
    return new SimpleAsyncTaskExecutor();
  }

  @Bean
  public SpringBootProcessApplication springBootServletProcessApplication() {
    return new SpringBootProcessApplication();
  }

  @Bean
  public RepositoryService repositoryService(ProcessEngine processEngine) throws Exception {
    return processEngine.getRepositoryService();
  }

  @Bean
  public RuntimeService runtimeService(ProcessEngine processEngine) throws Exception {
    return processEngine.getRuntimeService();
  }

  @Bean
  public TaskService taskService(ProcessEngine processEngine) throws Exception {
    return processEngine.getTaskService();
  }

  @Bean
  public HistoryService historyService(ProcessEngine processEngine) throws Exception {
    return processEngine.getHistoryService();
  }

  @Bean
  public ManagementService managementService(ProcessEngine processEngine) throws Exception {
    return processEngine.getManagementService();
  }

  @Bean
  public CaseService caseService(ProcessEngine processEngine) throws Exception {
    return processEngine.getCaseService();
  }

  @Bean
  public FilterService filterService(ProcessEngine processEngine) throws Exception {
    return processEngine.getFilterService();
  }

  @Bean
  public FormService formService(ProcessEngine processEngine) throws Exception {
    return processEngine.getFormService();
  }

  @Bean
  public IdentityService identityService(ProcessEngine processEngine) throws Exception {
    return processEngine.getIdentityService();
  }

  @Bean
  public AuthorizationService authorizationService(ProcessEngine processEngine) throws Exception {
    return processEngine.getAuthorizationService();
  }

}
