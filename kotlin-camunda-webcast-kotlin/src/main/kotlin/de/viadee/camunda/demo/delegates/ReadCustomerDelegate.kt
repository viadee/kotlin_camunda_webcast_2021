package de.viadee.camunda.demo.delegates

import org.springframework.beans.factory.annotation.Autowired
import de.viadee.camunda.demo.mapper.ServiceCallMapper
import org.camunda.bpm.engine.delegate.JavaDelegate
import kotlin.Throws
import org.camunda.bpm.engine.delegate.DelegateExecution
import de.viadee.camunda.demo.context.ExternalContext
import de.viadee.camunda.demo.dto.CustomerDTO
import de.viadee.camunda.demo.dto.AddressDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class ReadCustomerDelegate @Autowired constructor(private val mapper: ServiceCallMapper) : JavaDelegate {
    @Throws(Exception::class)
    override fun execute(delegateExecution: DelegateExecution) {
        val context = ExternalContext(delegateExecution)
        LOGGER.info("Read customer with {}", context.customerId)
        val customerDTO = customer
        mapper.map(context, customerDTO)
    }

    private val customer: CustomerDTO
        get() {
            val customerDTO = CustomerDTO()
            customerDTO.firstname = "Max"
            customerDTO.lastname = "Musterfrau"
            val addressDTO = AddressDTO()
            addressDTO.street = "Konrad-Adenauer-Ufer 7"
            addressDTO.zipCode = "50668"
            addressDTO.city = "Köln"
            addressDTO.country = "Germany"
            customerDTO.address = addressDTO
            return customerDTO
        }

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val LOGGER = LoggerFactory.getLogger(javaClass.enclosingClass)
    }
}