package org.camunda.bpm.extension.spring.boot.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleCamundaBootRestApplication.class)
@WebIntegrationTest(randomPort = true)
@DirtiesContext
public class SampleCamundaBootRestApplicationTest {

  @Value("${local.server.port}")
  private int port;

  @Test
  public void restApiIsAvailable() throws Exception {
    ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
      "http://localhost:" + port + "/api/engine/", String.class);
    assertEquals(HttpStatus.OK, entity.getStatusCode());
    assertEquals("[{\"name\":\"camunda-bpm-spring-boot-starter-rest\"}]", entity.getBody());
  }

}
