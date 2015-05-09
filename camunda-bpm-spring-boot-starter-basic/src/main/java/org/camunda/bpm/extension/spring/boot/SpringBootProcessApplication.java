package org.camunda.bpm.extension.spring.boot;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessApplicationDeployment;
import org.camunda.bpm.engine.repository.ProcessApplicationDeploymentBuilder;
import org.camunda.bpm.engine.spring.application.SpringProcessApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Process Application based on Spring Boot design principles and functionality.
 */
public class SpringBootProcessApplication extends SpringProcessApplication implements BeanNameAware {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CamundaProperties camundaProperties;
  @Autowired
  private ManagementService managementService;
  @Autowired
  private RepositoryService repositoryService;
  @Autowired
  private ResourcePatternResolver resourceLoader;

  @Value("${spring.application.name}")
  protected String applicationName;

  protected String beanName;

  @Override
  public void deploy() {
    ProcessApplicationDeploymentBuilder deploymentBuilder = repositoryService.createDeployment(getReference());

    deploymentBuilder.enableDuplicateFiltering(camundaProperties.isDeployChangedOnly())
      .name(applicationName);

    ArrayList<Resource> processes = new ArrayList<Resource>();

    String[] fileExtensions = camundaProperties.getProcesses().split(";");
    for (String fileExtension : fileExtensions) {

      Resource[] foundProcesses = new Resource[0];
      try {
        foundProcesses = resourceLoader.getResources(fileExtension);
      } catch (IOException e) {
        e.printStackTrace();
      }

      if (foundProcesses.length > 0) {
        processes.addAll(Arrays.asList(foundProcesses));
      }
    }

    if (camundaProperties.isResumePreviousVersions()) {
      deploymentBuilder.resumePreviousVersions();
    }

    for (Resource process : processes) {
      try {
        deploymentBuilder.addInputStream(process.getFilename(), process.getInputStream());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    ProcessApplicationDeployment deployment = deploymentBuilder.deploy();

    managementService.registerProcessApplication(deployment.getId(), getReference());
  }

  @Override
  public void setBeanName(String name) {
    super.setBeanName(name);
  }

  @Override
  protected String autodetectProcessApplicationName() {
    return applicationName != null ? applicationName : beanName;
  }

  protected void logDeploymentSummary(Collection<String> deploymentResourceNames, String deploymentName) {
    StringBuilder builder = new StringBuilder();
    builder.append("Deployment summary for process archive '"+deploymentName+"': \n");
    builder.append("\n");
    for (String resourceName : deploymentResourceNames) {
      builder.append("        "+resourceName);
      builder.append("\n");
    }
    LOGGER.info(builder.toString());
  }
}
