package commands;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.commons.utils.IoUtil;
import org.crsh.cli.Argument;
import org.crsh.cli.Command;
import org.crsh.cli.Required;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.springframework.beans.factory.BeanFactory;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

/**
 * Created by hawky4s on 16.03.15.
 */
@Usage("Camunda BPM extension")
public class camunda extends BaseCommand {

  @Usage("Deploy Process")
  @Command
  public Object deploy(
    @Usage("The process files to deploy")
    @Required
    @Argument
    List<File> files) {

    DeploymentBuilder deployment = getEngineService(RepositoryService.class).createDeployment();
    for (File file : files) {
      deployment.addInputStream(file.getName(), IoUtil.fileAsStream(file));
    }
    deployment.enableDuplicateFiltering(true);
    Deployment deployedDeployment = deployment.deploy();

    return deployedDeployment;
  }

  public <T> T getEngineService(T serviceClass) {
    BeanFactory factory = context.attributes['spring.beanfactory'];
    T service = factory.getBean(T)
    return service;
  }

}
