package de.viadee.camunda.demo

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
internal class WorkflowTest : AbstractProcessEngineRuleTest() {
    @Autowired
    private lateinit var runtimeService: RuntimeService

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @TestConfiguration
    internal class WorkflowTestConfig {
        @Bean
        fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
    }

    @Test
    fun readPath() {
        // given
        val variables = mapOf("customerId" to UUID.randomUUID().toString())

        // when
        val pi = restTemplate.postForObject(
            "http://localhost:$port/start/StartMessage",
            variables,
            String::class.java
        )

        // then
        val processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pi).singleResult()
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
        runtimeService.deleteProcessInstance(pi, "Test done")
    }

    @Test
    fun createPath() {
        // given
        val variables = mapOf("firstname" to "Melanie",
            "lastname" to "Mustermann",
            "zipcode" to "40227",
            "city" to "Düsseldorf")

        // when
        val pi = restTemplate.postForObject(
            "http://localhost:$port/start/StartMessage",
            variables,
            String::class.java
        )

        // then
        val processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pi).singleResult()
        BpmnAwareTests.assertThat(processInstance).isStarted
            .hasPassed("Gateway_0ohuh60", "Activity_18y6av5")
            .hasVariables("int_startBy", "ext_customerId", "ext_firstname", "ext_lastname", "ext_zipcode", "ext_city")
            .isWaitingAt("Event_0x8y0ce")
        runtimeService.deleteProcessInstance(pi, "Test done")
    }
}