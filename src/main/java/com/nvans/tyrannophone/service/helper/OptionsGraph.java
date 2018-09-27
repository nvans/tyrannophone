package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.entity.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OptionsGraph {

    @Autowired
    OptionDao optionDao;

    public Map<Option, Set<Option>> getAdj(Set<Option> options) {

        Map<Option, Set<Option>> adj = new HashMap<>();
        for(Option opt : options) {
            adj.put(opt, opt.getChildOptions());
        }

        return adj;
    }

    /**
     * Non recursive pre-order tree traversal.
     *
     * @param - parent option
     * @return set of children options and parent option.
     */
    public Set<Option> getAllChildren(Option option) {

        Set<Option> result = new HashSet<>();

        Deque<Option> optionDeque = new ArrayDeque<>();
        optionDeque.push(option);

        Option tmp;

        while (!optionDeque.isEmpty()) {

            tmp = optionDeque.pop();
            result.add(tmp);

            Set<Option> children = tmp.getChildOptions();

            if (children != null && !children.isEmpty()) {
                for(Option o : children) {
                    optionDeque.push(o);
                }
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Set<Option> getAllParents(Option option) {

        Option current = optionDao.findById(option.getId());

        Set<Option> parents = new HashSet<>();

        Option parent = current.getParentOption();

        while (parent != null) {
            parents.add(parent);
            parent = parent.getParentOption();
        }
        parents.add(current);

        return parents;
    }
}
