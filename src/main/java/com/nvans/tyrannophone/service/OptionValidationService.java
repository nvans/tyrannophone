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


    boolean hasValidHierarchy(Option option);

    /**
     * The method validates incompatible options set for the one option.
     *
     * @param options - options set.
     *
     * @return true if the set of options is valid, false otherwise.
     */
    boolean isOptionsCompatible(Set<Option> options);

    boolean haveParentChildRelation(Option opt1, Option opt2);

}
