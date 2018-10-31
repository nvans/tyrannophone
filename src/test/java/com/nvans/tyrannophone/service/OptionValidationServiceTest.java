package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.helper.CycleFinderService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import com.nvans.tyrannophone.service.implementation.OptionValidationServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
public class OptionValidationServiceTest {

    private static OptionDao optionDao = mock(OptionDao.class);

    private OptionsGraph optionsGraph = new OptionsGraph();

    private CycleFinderService cycleFinderService =
            new CycleFinderService(optionsGraph);

    private OptionValidationService optionValidationService =
            new OptionValidationServiceImpl(optionDao, optionsGraph, cycleFinderService);


    private static Set<Option> validHierarchy;
    private static Set<Option> invalidHierarchy;

    @BeforeClass
    public static void init() {

        validHierarchy = new HashSet<>();
        invalidHierarchy = new HashSet<>();

        // Options
        Option optA = new Option();
        Option optB = new Option();
        Option optC = new Option();
        Option optD = new Option();
        Option optE = new Option();
        Option optF = new Option();
        Option optG = new Option();

        optA.setName("A");
        optB.setName("B");
        optC.setName("C");
        optD.setName("D");
        optE.setName("E");
        optF.setName("F");
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
        Option optX = new Option();
        Option optY = new Option();
        Option optZ = new Option();


        // Invalid hierarchy
        // cycle: -> W -> X -> Y -> Z ->
        optW.setName("W");
        optX.setName("X");
        optY.setName("Y");
        optZ.setName("Z");

        invalidHierarchy.add(optW);
        invalidHierarchy.add(optX);
        invalidHierarchy.add(optY);
        invalidHierarchy.add(optZ);

        optW.setParentOption(optZ);
        optX.setParentOption(optW);
        optY.setParentOption(optX);
        optZ.setParentOption(optY);

        long id = 1L;
        for (Option opt : validHierarchy) {
            opt.setId(id);
            when(optionDao.findById(id)).thenReturn(opt);
            id++;

        }
        for (Option opt : invalidHierarchy) {
            opt.setId(id);
            when(optionDao.findById(id)).thenReturn(opt);
            id++;
        }


    }

    @Test
    public void validHierarchyTest() {

        validHierarchy.forEach(
                o -> assertTrue(optionValidationService.hasValidHierarchy(o)));
    }

    @Test
    public void invalidHierarchyTestHasCycle() {

        invalidHierarchy.forEach(
                o -> assertFalse(optionValidationService.hasValidHierarchy(o)));
    }

    @Test
    public void haveParentChildRelationTest() {
        Option optA = new Option();
        Option optB = new Option();
        Option optC = new Option();
        optA.setName("A");
        optB.setName("B");
        optC.setName("C");

        // Prepare hierarchy
        // A <- B <- C
        optB.setParentOption(optA);
        optC.setParentOption(optB);


        assertTrue(optionValidationService.haveParentChildRelation(optA, optA));
        assertTrue(optionValidationService.haveParentChildRelation(optB, optA));
        assertTrue(optionValidationService.haveParentChildRelation(optC, optB));


    }


}
