package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Option;

public interface OptionDao extends GenericDao<Option> {
    Option findByIdEager(Long optionId);
}
