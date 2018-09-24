package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.OptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOptionConverter implements Converter<String, Option> {

    private static final Logger logger = Logger.getLogger(StringToOptionConverter.class);

    @Autowired
    private OptionService optionService;

    @Override
    public Option convert(String s) {

        Long id = null;

        try {
            id = Long.parseLong(s);
            logger.debug("got id " + id);
        }
        catch (Exception ex) {
            logger.warn("can't convert", ex);
        }

        return id != null ? optionService.getOptionById(id) : null;
    }
}
