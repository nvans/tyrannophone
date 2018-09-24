package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.entity.Option;

import java.util.Set;

public interface OptionValidationService {

    /**
     * The method validates options dependencies.
     *
     * @param options - set of options.
     *
     * @return true if all dependencies are valid, false otherwise.
     */
    boolean isOptionsHierarchyValid(Set<Option> options);

    /**
     * The method validates incompatible options set for the one option.
     *
     * @param option - option.
     *
     * @return true if set of incompatible options is valid, false otherwise.
     */
    boolean isIncompatibleOptionsValid(Option option);

    boolean haveParentChildRelation(Option opt1, Option opt2);
}
