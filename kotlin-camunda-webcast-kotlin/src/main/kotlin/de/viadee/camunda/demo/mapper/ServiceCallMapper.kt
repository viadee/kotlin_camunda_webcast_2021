package de.viadee.camunda.demo.mapper

import de.viadee.camunda.demo.context.ExternalContext
import de.viadee.camunda.demo.dto.CustomerDTO
import org.mapstruct.*

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
interface ServiceCallMapper {
    @Mappings(
        Mapping(source = "street", target = "address.street"),
        Mapping(source = "zipCode", target = "address.zipCode"),
        Mapping(source = "city", target = "address.city"),
        Mapping(source = "country", target = "address.country")
    )
    fun map(externalContext: ExternalContext?): CustomerDTO?

    @InheritInverseConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings(
        Mapping(target = "customerId", ignore = true)
    )
    fun map(@MappingTarget externalContext: ExternalContext?, customer: CustomerDTO?)
}