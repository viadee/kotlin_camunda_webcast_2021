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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public void readPath() {
        // given
        final Map<String, Object> variables = Collections.singletonMap("customerId", UUID.randomUUID().toString());

        // when
        final String pi = restTemplate.postForObject(String.format("http://localhost:%d/start/StartMessage", port), variables, String.class);

        // then
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pi).singleResult();
        assertThat(processInstance).isStarted()
                .hasPassed("Gateway_0ohuh60", "Activity_1pxxd7s")
                .hasVariables("int_startBy", "ext_customerId", "ext_firstname", "ext_lastname", "ext_street", "ext_zipcode", "ext_city", "ext_country")
                .isWaitingAt("Event_0x8y0ce");

        runtimeService.deleteProcessInstance(pi, "Test done");
    }

    @Test
    public void createPath() {
        // given
        final Map<String, Object> variables = new HashMap<>(4);
        variables.put("firstname", "Melanie");
        variables.put("lastname", "Mustermann");
        variables.put("zipcode", "40227");
        variables.put("city", "Düsseldorf");

        // when
        final String pi = restTemplate.postForObject(String.format("http://localhost:%d/start/StartMessage", port), variables, String.class);

        // then
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pi).singleResult();
        assertThat(processInstance).isStarted()
                .hasPassed("Gateway_0ohuh60", "Activity_18y6av5")
                .hasVariables("int_startBy", "ext_customerId", "ext_firstname", "ext_lastname", "ext_zipcode", "ext_city")
                .isWaitingAt("Event_0x8y0ce");

        runtimeService.deleteProcessInstance(pi, "Test done");
    }

}
