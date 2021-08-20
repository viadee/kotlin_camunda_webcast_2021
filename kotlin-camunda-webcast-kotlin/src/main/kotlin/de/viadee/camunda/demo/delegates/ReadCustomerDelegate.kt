package de.viadee.camunda.demo.delegates

import de.viadee.camunda.demo.context.ExternalContext
import de.viadee.camunda.demo.dto.AddressDTO
import de.viadee.camunda.demo.dto.CustomerDTO
import de.viadee.camunda.demo.mapper.ServiceCallMapper
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReadCustomerDelegate @Autowired constructor(private val mapper: ServiceCallMapper) : JavaDelegate {
    @Throws(Exception::class)
    override fun execute(delegateExecution: DelegateExecution) {
        val context = ExternalContext(delegateExecution)
        LOGGER.info("Read customer with {}", context.customerId)
        mapper.map(context, customer)
    }

    private val customer: CustomerDTO
        get() = CustomerDTO("Max",
            "Musterfrau",
            AddressDTO("Konrad-Adenauer-Ufer 7", "50668", "Köln", "Germany"))

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val LOGGER = LoggerFactory.getLogger(javaClass.enclosingClass)
    }
}