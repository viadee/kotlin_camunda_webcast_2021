package de.viadee.camunda.demo.delegates;

import de.viadee.camunda.demo.context.ExternalContext;
import de.viadee.camunda.demo.dto.AddressDTO;
import de.viadee.camunda.demo.dto.CustomerDTO;
import de.viadee.camunda.demo.mapper.ServiceCallMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadCustomerDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCustomerDelegate.class);

    private final ServiceCallMapper mapper;

    @Autowired
    public ReadCustomerDelegate(final ServiceCallMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        final ExternalContext context = new ExternalContext(delegateExecution);

        LOGGER.info("Read customer with {}", context.getCustomerId());

        final CustomerDTO customerDTO = getCustomer();
        mapper.map(context, customerDTO);
    }

    private CustomerDTO getCustomer() {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Max");
        customerDTO.setLastname("Musterfrau");

        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Konrad-Adenauer-Ufer 7");
        addressDTO.setZipCode("50668");
        addressDTO.setCity("Köln");
        addressDTO.setCountry("Germany");
        customerDTO.setAddress(addressDTO);
        return customerDTO;
    }
}
