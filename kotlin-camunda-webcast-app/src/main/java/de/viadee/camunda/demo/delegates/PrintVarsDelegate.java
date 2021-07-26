package de.viadee.camunda.demo.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class PrintVarsDelegate implements JavaDelegate {

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        System.out.printf("Processinstance variables: %s%n", delegateExecution.getVariables().toString());
    }
}
