package com.nvans.tyrannophone.service.helper;

import com.nvans.tyrannophone.model.entity.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CycleFinderService {

    private final OptionsGraph optionsGraph;

    @Autowired
    public CycleFinderService(OptionsGraph optionsGraph) {
        this.optionsGraph = optionsGraph;
    }

    private enum State {
        WHITE, GRAY, BLACK
    }

    @Transactional(readOnly = true)
    public boolean hasCycle(Set<Option> optionHierarchy) {

        Map<Option, Set<Option>> adj = optionsGraph.getAdj(optionHierarchy);

        Map<Option, State> nodeStates = new HashMap<>();
        Map<Option, Boolean> visited = new HashMap<>();
        Deque<Option> toVisit = new ArrayDeque<>();
        int start, end = 0;

        for (Option opt : adj.keySet()) {
            nodeStates.put(opt, State.WHITE);
            visited.put(opt, Boolean.FALSE);
        }

        Option current = null;

        for (Option opt : adj.keySet()) {
            if (!visited.get(opt)) {
                visited.put(opt, Boolean.TRUE);
                toVisit.push(opt);

                while(!toVisit.isEmpty()) {
                    current = toVisit.pop();

                    visited.put(current, Boolean.TRUE);
                    nodeStates.put(current, State.GRAY);

                    start = end = toVisit.size();
                    for (Option child : current.getChildOptions()) {
                        if (nodeStates.get(child) == State.BLACK) continue;

                        end++;
                        if (nodeStates.get(child) == State.GRAY) {
                            return true;
                        }
                        toVisit.push(child);
                    }

                    if (start == end) {
                        nodeStates.put(current, State.BLACK);
                    }
                }
            }
        }

        return false;
    }

    @Transactional(readOnly = true)
    public boolean hasCycle(Map<Option, Set<Option>> adj) {

        Map<Option, State> nodeStates = new HashMap<>();
        Map<Option, Boolean> visited = new HashMap<>();
        Deque<Option> toVisit = new ArrayDeque<>();
        int start, end = 0;

        for (Option opt : adj.keySet()) {
            nodeStates.put(opt, State.WHITE);
            visited.put(opt, Boolean.FALSE);
        }

        Option current = null;

        for (Option opt : adj.keySet()) {
            if (!visited.get(opt)) {
                visited.put(opt, Boolean.TRUE);
                toVisit.push(opt);

                while(!toVisit.isEmpty()) {
                    current = toVisit.pop();

                    visited.put(current, Boolean.TRUE);
                    nodeStates.put(current, State.GRAY);

                    start = end = toVisit.size();
                    for (Option child : current.getChildOptions()) {
                        if (nodeStates.get(child) == State.BLACK) continue;

                        end++;
                        if (nodeStates.get(child) == State.GRAY) {
                            return true;
                        }
                        toVisit.push(child);
                    }

                    if (start == end) {
                        nodeStates.put(current, State.BLACK);
                    }
                }
            }
        }

        return false;
    }
}
