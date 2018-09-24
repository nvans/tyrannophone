package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.helper.CycleFinderService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionValidationServiceSubComponentsTest {


    private OptionsGraph optionsGraph = new OptionsGraph();
    private CycleFinderService cycleFinderService = new CycleFinderService();
    private Set<Option> validHierarchy = new HashSet<>();

    private Set<Option> invalidHierarchy = new HashSet<>();


    @Before
    public void init() {
        // Options
        Option optA = new Option();
        optA.setName("A");
        Option optB = new Option();
        optB.setName("B");
        Option optC = new Option();
        optC.setName("C");
        Option optD = new Option();
        optD.setName("D");
        Option optE = new Option();
        optE.setName("E");
        Option optF = new Option();
        optF.setName("F");
        Option optG = new Option();
        optG.setName("G");

        validHierarchy.add(optA);
        validHierarchy.add(optB);
        validHierarchy.add(optC);
        validHierarchy.add(optD);
        validHierarchy.add(optE);
        validHierarchy.add(optF);
        validHierarchy.add(optG);

        // Hierarchy
        // A: B, C
        // B: F
        // C: E, D
        // D: G
        // E:
        // F:
        // G:
        optA.setParentOption(null);

        optB.setParentOption(optA);
        optF.setParentOption(optB);

        optC.setParentOption(optA);
        optD.setParentOption(optC);
        optG.setParentOption(optD);
        optE.setParentOption(optC);


        Option optW = new Option();
        optW.setName("W");
        Option optX = new Option();
        optX.setName("X");
        Option optY = new Option();
        optY.setName("Y");
        Option optZ = new Option();
        optZ.setName("Z");

        invalidHierarchy.add(optW);
        invalidHierarchy.add(optX);
        invalidHierarchy.add(optY);
        invalidHierarchy.add(optZ);

        optW.setParentOption(optZ);
        optX.setParentOption(optW);
        optY.setParentOption(optX);
        optZ.setParentOption(optY);

    }

    @Test
    public void testValidHierarchy() {
        assertTrue(!cycleFinderService.hasCycle(optionsGraph.getAdj(validHierarchy)));
    }

    @Test
    public void testInvalidHierarchy() {
        assertFalse(!cycleFinderService.hasCycle(optionsGraph.getAdj(invalidHierarchy)));
    }



}
