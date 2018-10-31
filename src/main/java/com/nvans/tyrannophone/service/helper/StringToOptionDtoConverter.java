package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.service.OptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOptionDtoConverter implements Converter<String, PlanOptionDto> {

    private static final Logger log = Logger.getLogger(StringToOptionDtoConverter.class);

    @Autowired
    private OptionService optionService;

    @Override
    public PlanOptionDto convert(String name) {

        if (name == null || name.isEmpty()) {
            return null;
        }

        return new PlanOptionDto(optionService.getOptionByName(name));
    }
}
