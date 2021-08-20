package de.viadee.camunda.demo.context

import org.camunda.bpm.engine.delegate.DelegateExecution

class ExternalContext(private val delegateExecution: DelegateExecution) {
    var customerId: String
        get() = delegateExecution.getVariable(VAR_CUSTOMER_ID) as String
        set(customerId) {
            delegateExecution.setVariable(VAR_CUSTOMER_ID, customerId)
        }
    var firstname: String
        get() = delegateExecution.getVariable(VAR_FIRSTNAME) as String
        set(firstname) {
            delegateExecution.setVariable(VAR_FIRSTNAME, firstname)
        }
    var lastname: String
        get() = delegateExecution.getVariable(VAR_LASTNAME) as String
        set(lastname) {
            delegateExecution.setVariable(VAR_LASTNAME, lastname)
        }
    var street: String?
        get() = delegateExecution.getVariable(VAR_STREET) as String?
        set(street) {
            delegateExecution.setVariable(VAR_STREET, street)
        }
    var zipCode: String
        get() = delegateExecution.getVariable(VAR_ZIP_CODE) as String
        set(zipCode) {
            delegateExecution.setVariable(VAR_ZIP_CODE, zipCode)
        }
    var city: String
        get() = delegateExecution.getVariable(VAR_CITY) as String
        set(city) {
            delegateExecution.setVariable(VAR_CITY, city)
        }
    var country: String
        get() = delegateExecution.getVariable(VAR_CITY) as String
        set(country) {
            delegateExecution.setVariable(VAR_COUNTRY, country)
        }

    companion object {
        private const val VAR_CUSTOMER_ID = "ext_customerId"
        private const val VAR_FIRSTNAME = "ext_firstname"
        private const val VAR_LASTNAME = "ext_lastname"
        private const val VAR_STREET = "ext_street"
        private const val VAR_ZIP_CODE = "ext_zipcode"
        private const val VAR_CITY = "ext_city"
        private const val VAR_COUNTRY = "ext_country"
    }
}