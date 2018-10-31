package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.entity.Option;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OptionsGraph {


    private static final Logger log = Logger.getLogger(OptionsGraph.class);

    /**
     * Builds adjacency matrix for options set
     *
     * @param options options set to build adjacency matrix
     * @return map represented adjacency matrix
     */
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Map<Option, Set<Option>> getAdj(Set<Option> options) {

        log.info("Getting adjacent matrix");

        Map<Option, Set<Option>> adj = new HashMap<>();
        for(Option opt : options) {
            adj.put(opt, opt.getChildOptions());
        }

        return adj;
    }

    /**
     * Retrieves set of children.
     * Non recursive pre-order tree traversal implementation.
     *
     * @param option - parent option
     * @return set of children options.
     * @throws TyrannophoneException if cycle detected.
     */
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Set<Option> getChildren(Option option) {

        log.info("Getting children");

        Set<Option> children = new HashSet<>();
        Deque<Option> optionDeque = new ArrayDeque<>();

        // Add all children of current option to stack
        option.getChildOptions().forEach(optionDeque::push);

        Option tmp = null;

        // add all children hierarchy to result
        while (!(optionDeque.isEmpty())) {
            tmp = optionDeque.pop();

            if (tmp.equals(option)) {
                throw new TyrannophoneException("Has cycle");
            }

            children.add(tmp);

            // add children of this child to stack
            tmp.getChildOptions().forEach(optionDeque::push);
        }

        return children;
    }

    /**
     * Retrieve set of parents for given option.
     *
     * @param option - option
     * @return set of parent options and current option.
     * @throws TyrannophoneException if cycle detected.
     */
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Set<Option> getParents(Option option) {

        log.info("Getting parents");

        Set<Option> parents = new HashSet<>();

        Option parent = option.getParentOption();

        while (parent != null) {

            if (parent.equals(option)) {
                throw new TyrannophoneException("Has cycle");
            }

            parents.add(parent);
            parent = parent.getParentOption();
        }

        return parents;
    }
}
