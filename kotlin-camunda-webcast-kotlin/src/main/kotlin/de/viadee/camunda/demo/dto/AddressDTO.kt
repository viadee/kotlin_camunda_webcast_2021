package de.viadee.camunda.demo.dto

import java.util.*

class AddressDTO {
    var street: String? = null
    var zipCode: String? = null
    var city: String? = null
    var country: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val address = other as AddressDTO
        return street == address.street && zipCode == address.zipCode && city == address.city && country == address.country
    }

    override fun hashCode(): Int {
        return Objects.hash(street, zipCode, city, country)
    }

    override fun toString(): String {
        return "Address{" +
                "street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}'
    }
}