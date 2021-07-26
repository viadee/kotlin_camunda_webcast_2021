package de.viadee.camunda.demo.delegates;

import de.viadee.camunda.demo.context.ExternalContext;
import de.viadee.camunda.demo.dto.CustomerDTO;
import de.viadee.camunda.demo.mapper.ServiceCallMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateCustomerDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCustomerDelegate.class);

    private final ServiceCallMapper mapper;

    @Autowired
    public CreateCustomerDelegate(final ServiceCallMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        final ExternalContext context = new ExternalContext(delegateExecution);

        final CustomerDTO customerDTO = mapper.map(context);
        LOGGER.info("Create customer with {}", customerDTO);

        context.setCustomerId(UUID.randomUUID().toString());
    }
}
