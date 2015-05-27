package org.camunda.bpm.spring.boot.example;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Calendar;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.variable.Variables.createVariables;

@SpringBootApplication
public class CamundaBpmExampleApplication {

  Logger logger = Logger.getLogger(this.getClass().getName());

  public static void main(String[] args) {
    new SpringApplicationBuilder(CamundaBpmExampleApplication.class)
      .run();

  }


  private void startProcessInstance(ProcessEngine processEngine) {
    // process instance 1
    processEngine.getRuntimeService().startProcessInstanceByKey("invoice", createVariables()
      .putValue("creditor", "Great Pizza for Everyone Inc.")
      .putValue("amount", "30€")
      .putValue("invoiceNumber", "GPFE-23232323"));

    // process instance 2
    ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("invoice", createVariables()
      .putValue("creditor", "Bobby's Office Supplies")
      .putValue("amount", "312.99$")
      .putValue("invoiceNumber", "BOS-43934"));
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, -14);
      ClockUtil.setCurrentTime(calendar.getTime());
      processEngine.getIdentityService().setAuthenticatedUserId("demo");
      Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
      processEngine.getTaskService().complete(task.getId(), createVariables().putValue("approver", "john"));
    }
    finally{
      ClockUtil.reset();
      processEngine.getIdentityService().clearAuthentication();
    }

    // process instance 3
    pi = processEngine.getRuntimeService().startProcessInstanceByKey("invoice", createVariables()
      .putValue("creditor", "Papa Steve's all you can eat")
      .putValue("invoiceNumber", "PSACE-5342"));
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, -5);
      ClockUtil.setCurrentTime(calendar.getTime());
      processEngine.getIdentityService().setAuthenticatedUserId("demo");
      Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
      processEngine.getTaskService().complete(task.getId(), createVariables().putValue("approver", "mary"));
    }
    finally{
      processEngine.getIdentityService().clearAuthentication();
    }
    try {
      processEngine.getIdentityService().setAuthenticatedUserId("mary");
      Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
      processEngine.getTaskService().createComment(null, pi.getId(), "I cannot approve this invoice: the amount is missing.\n\n Could you please provide the amount?");
      processEngine.getTaskService().complete(task.getId(), createVariables().putValue("approved", "false"));
    }
    finally{
      ClockUtil.reset();
      processEngine.getIdentityService().clearAuthentication();
    }
  }

  private void createUsers(ProcessEngine processEngine) {

    // create demo users
    new DemoDataGenerator().createUsers(processEngine);
  }

}
