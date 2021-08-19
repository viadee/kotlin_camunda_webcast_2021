package de.viadee.camunda.demo.mapper;

import de.viadee.camunda.demo.context.ExternalContext;
import de.viadee.camunda.demo.dto.CustomerDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface ServiceCallMapper {
    @Mappings({
            @Mapping(source = "street", target = "address.street"),
            @Mapping(source = "zipCode", target = "address.zipCode"),
            @Mapping(source = "city", target = "address.city"),
            @Mapping(source = "country", target = "address.country")
    })
    CustomerDTO map(final ExternalContext externalContext);

    @InheritInverseConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "customerId", ignore = true)
    })
    void map(@MappingTarget final ExternalContext externalContext, final CustomerDTO customer);
}
