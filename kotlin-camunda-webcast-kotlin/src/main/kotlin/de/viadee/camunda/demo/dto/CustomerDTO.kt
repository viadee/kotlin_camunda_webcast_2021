package de.viadee.camunda.demo.dto

import java.util.*

class CustomerDTO {
    var firstname: String? = null
    var lastname: String? = null
    var address: AddressDTO? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val customer = other as CustomerDTO
        return firstname == customer.firstname && lastname == customer.lastname && address == customer.address
    }

    override fun hashCode(): Int {
        return Objects.hash(firstname, lastname, address)
    }

    override fun toString(): String {
        return "Customer{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address=" + address +
                '}'
    }
}