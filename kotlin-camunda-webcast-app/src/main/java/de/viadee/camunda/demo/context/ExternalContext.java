package de.viadee.camunda.demo.context;

import org.camunda.bpm.engine.delegate.DelegateExecution;

public class ExternalContext {
    private static final String VAR_CUSTOMER_ID = "ext_customerId";
    private static final String VAR_FIRSTNAME = "ext_firstname";
    private static final String VAR_LASTNAME = "ext_lastname";
    private static final String VAR_STREET = "ext_street";
    private static final String VAR_ZIP_CODE = "ext_zipcode";
    private static final String VAR_CITY = "ext_city";
    private static final String VAR_COUNTRY = "ext_country";

    private final DelegateExecution delegateExecution;

    public ExternalContext(final DelegateExecution delegateExecution) {
        this.delegateExecution = delegateExecution;
    }

    public String getCustomerId() {
        return (String) delegateExecution.getVariable(VAR_CUSTOMER_ID);
    }

    public void setCustomerId(final String customerId) {
        delegateExecution.setVariable(VAR_CUSTOMER_ID, customerId);
    }


    public String getFirstname() {
        return (String) delegateExecution.getVariable(VAR_FIRSTNAME);
    }

    public void setFirstname(final String firstname) {
        delegateExecution.setVariable(VAR_FIRSTNAME, firstname);
    }

    public String getLastname() {
        return (String) delegateExecution.getVariable(VAR_LASTNAME);
    }

    public void setLastname(final String lastname) {
        delegateExecution.setVariable(VAR_LASTNAME, lastname);
    }

    public String getStreet() {
        return (String) delegateExecution.getVariable(VAR_STREET);
    }

    public void setStreet(final String street) {
        delegateExecution.setVariable(VAR_STREET, street);
    }

    public String getZipCode() {
        return (String) delegateExecution.getVariable(VAR_ZIP_CODE);
    }

    public void setZipCode(final String zipCode) {
        delegateExecution.setVariable(VAR_ZIP_CODE, zipCode);
    }

    public String getCity() {
        return (String) delegateExecution.getVariable(VAR_CITY);
    }

    public void setCity(final String city) {
        delegateExecution.setVariable(VAR_CITY, city);
    }

    public String getCountry() {
        return (String) delegateExecution.getVariable(VAR_CITY);
    }

    public void setCountry(final String country) {
        delegateExecution.setVariable(VAR_COUNTRY, country);
    }
}
