package de.viadee.camunda.demo;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WorkflowTest extends AbstractProcessEngineRuleTest {

    @Autowired
    public RuntimeService runtimeService;

    @LocalServerPort
    public int port;

    @Autowired
    public RestTemplate restTemplate;

    @TestConfiguration
    public static class WorkflowTestConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplateBuilder().build();
        }
    }

    @Test
    public void shouldExecuteHappyPath() {
        // given
        final Map<String, Object> variables = new HashMap<>(3);
        variables.put("val1", "foo");
        variables.put("val2", "bar");
        variables.put("val3", "foobar");

        // when
        final String pi = restTemplate.postForObject(String.format("http://localhost:%d/start/StartMessage", port), variables, String.class);

        // then
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pi).singleResult();
        assertThat(processInstance).isStarted()
                .hasPassed("Activity_1pxxd7s")
                .hasVariables("val1", "val2", "val3", "startBy")
                .task()
                .hasDefinitionKey("say-hello")
                .hasCandidateUser("demo")
                .isNotAssigned();
    }

}
