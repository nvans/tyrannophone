package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCustomerConverter implements Converter<String, Customer> {

    private static final Logger logger = Logger.getLogger(StringToCustomerConverter.class);

    @Autowired
    private CustomerService customerService;

    @Override
    public Customer convert(String email) {

        return customerService.getCustomerByEmail(email);
    }
}
