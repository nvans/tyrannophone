package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.Option;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class OptionSet implements Serializable {

    private Set<Option> options;

    public OptionSet() {
        this.options = new HashSet<>();
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }
}
