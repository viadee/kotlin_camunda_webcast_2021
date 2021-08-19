package de.viadee.camunda.demo

import org.springframework.boot.test.context.SpringBootTest
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest
import org.springframework.beans.factory.annotation.Autowired
import org.camunda.bpm.engine.RuntimeService
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests
import org.junit.Test
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
class WorkflowTest : AbstractProcessEngineRuleTest() {
    @Autowired
    var runtimeService: RuntimeService? = null

    @LocalServerPort
    var port = 0

    @Autowired
    var restTemplate: RestTemplate? = null

    @TestConfiguration
    class WorkflowTestConfig {
        @Bean
        fun restTemplate(): RestTemplate {
            return RestTemplateBuilder().build()
        }
    }

    @Test
    fun readPath() {
        // given
        val variables = Collections.singletonMap<String, Any>("customerId", UUID.randomUUID().toString())

        // when
        val pi = restTemplate!!.postForObject(
            String.format("http://localhost:%d/start/StartMessage", port),
            variables,
            String::class.java
        )

        // then
        val processInstance = runtimeService!!.createProcessInstanceQuery().processInstanceId(pi).singleResult()
        BpmnAwareTests.assertThat(processInstance).isStarted
            .hasPassed("Gateway_0ohuh60", "Activity_1pxxd7s")
            .hasVariables(
                "int_startBy",
                "ext_customerId",
                "ext_firstname",
                "ext_lastname",
                "ext_street",
                "ext_zipcode",
                "ext_city",
                "ext_country"
            )
            .isWaitingAt("Event_0x8y0ce")
        runtimeService!!.deleteProcessInstance(pi, "Test done")
    }

    @Test
    fun createPath() {
        // given
        val variables: MutableMap<String, Any> = HashMap(4)
        variables["firstname"] = "Melanie"
        variables["lastname"] = "Mustermann"
        variables["zipcode"] = "40227"
        variables["city"] = "Düsseldorf"

        // when
        val pi = restTemplate!!.postForObject(
            String.format("http://localhost:%d/start/StartMessage", port),
            variables,
            String::class.java
        )

        // then
        val processInstance = runtimeService!!.createProcessInstanceQuery().processInstanceId(pi).singleResult()
        BpmnAwareTests.assertThat(processInstance).isStarted
            .hasPassed("Gateway_0ohuh60", "Activity_18y6av5")
            .hasVariables("int_startBy", "ext_customerId", "ext_firstname", "ext_lastname", "ext_zipcode", "ext_city")
            .isWaitingAt("Event_0x8y0ce")
        runtimeService!!.deleteProcessInstance(pi, "Test done")
    }
}