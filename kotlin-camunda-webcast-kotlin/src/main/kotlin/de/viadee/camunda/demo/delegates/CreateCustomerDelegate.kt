package de.viadee.camunda.demo.delegates

import org.springframework.beans.factory.annotation.Autowired
import de.viadee.camunda.demo.mapper.ServiceCallMapper
import org.camunda.bpm.engine.delegate.JavaDelegate
import kotlin.Throws
import org.camunda.bpm.engine.delegate.DelegateExecution
import de.viadee.camunda.demo.context.ExternalContext
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class CreateCustomerDelegate @Autowired constructor(private val mapper: ServiceCallMapper) : JavaDelegate {
    @Throws(Exception::class)
    override fun execute(delegateExecution: DelegateExecution) {
        with(ExternalContext(delegateExecution)) {
            val customerDTO = mapper.map(this)
            LOGGER.info("Create customer with {}", customerDTO)
            customerId = UUID.randomUUID().toString()
        }

    }

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val LOGGER = LoggerFactory.getLogger(javaClass.enclosingClass)
    }
}