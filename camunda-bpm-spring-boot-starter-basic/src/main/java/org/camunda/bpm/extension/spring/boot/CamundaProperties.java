package org.camunda.bpm.extension.spring.boot;

import org.camunda.bpm.application.impl.metadata.ProcessArchiveXmlImpl;
import org.camunda.bpm.application.impl.metadata.ProcessesXmlImpl;
import org.camunda.bpm.container.impl.jmx.services.JmxManagedProcessEngineController;
import org.camunda.bpm.container.impl.metadata.*;
import org.camunda.bpm.container.impl.metadata.spi.ProcessEnginePluginXml;
import org.camunda.bpm.container.impl.metadata.spi.ProcessEngineXml;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.FoxFailedJobParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.impl.jobexecutor.FoxFailedJobCommandFactory;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Stuff from
 *  - @link org.camunda.bpm.container.impl.deployment.StartProcessEngineStep
 *  -
 *
 */
@ConfigurationProperties(prefix = "camunda", ignoreUnknownFields = true)
public class CamundaProperties {

  private static final String LOOKUP_PROCESSES_WITH_EXTENSION =
    "classpath*:*.bpmn;" +
    "classpath*:*.bpmn20.xml;" +
    "classpath*:*.cmmn";

  @Autowired
  private Environment environment;

  protected BpmPlatformXmlImpl bpmPlatformXml;
  protected ProcessesXmlImpl processesXml;

  @NestedConfigurationProperty
  protected ProcessArchiveXmlImpl processArchiveXml;
  @NestedConfigurationProperty
  protected JobExecutorXmlImpl jobExecutorXml;
  @NestedConfigurationProperty
  protected JobAcquisitionXmlImpl jobAcquisitionXml;
  @NestedConfigurationProperty
  protected ProcessEngineXmlImpl processEngineXml;
  @NestedConfigurationProperty
  protected List<ProcessEnginePluginXmlImpl> processEnginePluginXml;

  private boolean cmmnEnabled = true;
  private boolean jobExecutorDeploymentAware = true;
  private boolean jobExecutorActive = true;
  protected String databaseSchemaUpdate = ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;
  private boolean resumePreviousVersions;
  private boolean deployChangedOnly;
  private String processes;

  public boolean isCmmnEnabled() {
    return cmmnEnabled;
  }

  public boolean isJobExecutorDeploymentAware() {
    return jobExecutorDeploymentAware;
  }

  public boolean isJobExecutorActive() {
    return jobExecutorActive;
  }

  public void apply(ProcessEngineConfigurationImpl configuration) {
    configuration.setIdGenerator(new StrongUuidGenerator());

    configuration.setCmmnEnabled(cmmnEnabled);
    configuration.setHistory(HistoryLevel.HISTORY_LEVEL_FULL.getName());
    configuration.setDatabaseSchema("");
    configuration.setDatabaseTablePrefix("");
    configuration.setJobExecutorDeploymentAware(jobExecutorDeploymentAware);
    configuration.setAutoStoreScriptVariables(false);
    configuration.setDefaultNumberOfRetries(3);
    configuration.setDataSourceJndiName("");
    configuration.setAuthorizationEnabled(false);
    configuration.setCreateIncidentOnFailedJobEnabled(true);
    configuration.setProcessEngineName("default");
//    configuration.setProcessEngineName(environment.getProperty("spring.application.name", "default"));

    PropertyHelper.applyProperties(configuration, properties);

    // register Spin and Connect
    List<ProcessEnginePlugin> processEnginePlugins = configuration.getProcessEnginePlugins();
    if (processEnginePlugins == null) {
      processEnginePlugins = new ArrayList<ProcessEnginePlugin>();
    }
    processEnginePlugins.add(new SpinProcessEnginePlugin());
    processEnginePlugins.add(new ConnectProcessEnginePlugin());
  }

  public String getDatabaseSchemaUpdate() {
    return databaseSchemaUpdate;
  }

  public void setDatabaseSchemaUpdate(String databaseSchemaUpdate) {
    this.databaseSchemaUpdate = databaseSchemaUpdate;
  }

  public boolean isResumePreviousVersions() {
    return resumePreviousVersions;
  }

  public void setResumePreviousVersions(boolean resumePreviousVersions) {
    this.resumePreviousVersions = resumePreviousVersions;
  }

  public boolean isDeployChangedOnly() {
    return deployChangedOnly;
  }

  public void setDeployChangedOnly(boolean deployChangedOnly) {
    this.deployChangedOnly = deployChangedOnly;
  }

  public String getProcesses() {
    if (processes == null) {
      return LOOKUP_PROCESSES_WITH_EXTENSION;
    }
    return processes;
  }

  public void setProcesses(String processes) {
    this.processes = processes;
  }

  protected void configureCustomRetryStrategy(ProcessEngineConfigurationImpl configurationImpl) {
    // add support for custom Retry strategy
    // TODO: decide whether this should be moved  to configuration or to plugin
    List<BpmnParseListener> customPostBPMNParseListeners = configurationImpl.getCustomPostBPMNParseListeners();
    if(customPostBPMNParseListeners==null) {
      customPostBPMNParseListeners = new ArrayList<BpmnParseListener>();
      configurationImpl.setCustomPostBPMNParseListeners(customPostBPMNParseListeners);
    }
    customPostBPMNParseListeners.add(new FoxFailedJobParseListener());
    configurationImpl.setFailedJobCommandFactory(new FoxFailedJobCommandFactory());
  }

  protected JmxManagedProcessEngineController createProcessEngineControllerInstance(ProcessEngineConfigurationImpl configuration) {
    return new JmxManagedProcessEngineController(configuration);
  }

  /**
   * <p>Instantiates and applies all {@link ProcessEnginePlugin}s defined in the processEngineXml
   */
  protected void configurePlugins(ProcessEngineConfigurationImpl configuration, ProcessEngineXml processEngineXml, ClassLoader classLoader) {

    for (ProcessEnginePluginXml pluginXml : processEngineXml.getPlugins()) {
      // create plugin instance
      Class<? extends ProcessEnginePlugin> pluginClass = loadClass(pluginXml.getPluginClass(), classLoader, ProcessEnginePlugin.class);
      ProcessEnginePlugin plugin = createInstance(pluginClass);

      // apply configured properties
      Map<String, String> properties = pluginXml.getProperties();
      PropertyHelper.applyProperties(plugin, properties);

      // add to configuration
      configuration.getProcessEnginePlugins().add(plugin);
    }
  }
}
