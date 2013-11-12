package com.google.gwt.sample.validation.server;

import org.hibernate.validator.HibernateValidator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.spi.ValidationProvider;

public class OsgiValidation implements ValidationProviderResolver {

  public static Validator getValidator() {
    Configuration<?> config =
        Validation.byDefaultProvider().providerResolver(new OsgiValidation()).configure();

    ValidatorFactory factory = config.buildValidatorFactory();
    Validator validator = factory.getValidator();
    
    return validator;
  }

  @Override
  public List<ValidationProvider<?>> getValidationProviders() {
    List<ValidationProvider<?>> ret = new ArrayList<>();
    ret.add(new HibernateValidator());
    return ret; 
  }

}
