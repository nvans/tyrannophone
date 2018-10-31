package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import com.nvans.tyrannophone.service.helper.CycleFinderService;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import com.nvans.tyrannophone.service.implementation.OptionServiceImpl;
import com.nvans.tyrannophone.service.implementation.OptionValidationServiceImpl;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OptionServiceTest {

    private PlanDao planDao = mock(PlanDao.class);
    private static OptionDao optionDao = mock(OptionDao.class);

    private OptionsGraph optionsGraph = new OptionsGraph();
    private CycleFinderService cycleFinderService = new CycleFinderService(optionsGraph);
    private OptionValidationService optionValidationService =
            new OptionValidationServiceImpl(optionDao, optionsGraph, cycleFinderService);


    private OptionService optionService =
            new OptionServiceImpl(planDao, optionDao, optionValidationService, optionsGraph);


    private Option optA, optB, optC, optD;
    private List<Option> options;


    @Before
    public void init() {

        optA = new Option();
        optB = new Option();
        optC = new Option();
        optD = new Option();

        optA.setName("A");
        optB.setName("B");
        optC.setName("C");
        optD.setName("D");

        options = new ArrayList<>();
        options.add(optA);
        options.add(optB);
        options.add(optC);
        options.add(optD);

        long id = 1;

        for (Option opt : options) {

            opt.setId(id);

            when(optionDao.findById(id)).thenReturn(opt);
            when(optionDao.findByIdEager(id)).thenReturn(opt);
            when(optionDao.findByName(opt.getName())).thenReturn(opt);

            id++;
        }
        when(optionDao.findAll()).thenReturn(options);

        Plan plan = new Plan();
        plan.setId(1L);
        plan.setPlanName("Test plan");
        plan.setConnectionPrice(0);
        plan.setMonthlyPrice(0);
        plan.setConnectionAvailable(true);

        when(planDao.findById(1L)).thenReturn(plan);
        when(planDao.findByName("Test plan")).thenReturn(plan);

    }

    @Test
    public void getAllOptionsTest() {
        List<Option> actualOptions = optionService.getAllOptions();

        assertTrue(options.containsAll(actualOptions));
        assertTrue(actualOptions.containsAll(options));
    }

    @Test
    public void updateCompatibilityTest() {

        // Mock of persistent object for optA
        Option optApo = new Option() {{
            setId(optA.getId());
            setName(optA.getName());
        }};
        when(optionDao.findByIdEager(optA.getId())).thenReturn(optApo);

        // Create hierarchy b -> c, d
        optB.setChildOptions(new HashSet<>(Arrays.asList(optC, optD)));

        // Add option B as incompatible to option A
        Set<Option> incompatibleSet = new HashSet<>(Collections.singletonList(optB));
        optA.setIncompatibleOptions(incompatibleSet);

        // Call service
        optionService.updateCompatibility(optA);
        // Simulate persisting
        optA = optApo;

        // 'A' has to be incompatible with 'B', 'C', 'D' and vice versa.
        assertTrue(optA.getIncompatibleOptions().containsAll(Arrays.asList(optB, optC, optD)));
        assertTrue(optB.getIncompatibleOptions().contains(optA));
        assertTrue(optC.getIncompatibleOptions().contains(optA));
        assertTrue(optD.getIncompatibleOptions().contains(optA));
    }

    @Test
    public void getIncompatibleOptionsNamesTest() {

        optA.setIncompatibleOptions(new HashSet<>(Arrays.asList(optB, optC, optD)));

        List<String> expectedNames = Arrays.asList("B", "C", "D");
        List<String> actualNames = optionService.getIncompatibleOptionsNames(optA.getName());


        assertTrue(expectedNames.containsAll(actualNames));
        assertTrue(actualNames.containsAll(expectedNames));

    }


    @Test
    public void getAllowableIncompatibleOptionsSetTest() {
        // Prepare hierarchy
        //    A       B
        //    |       |
        //    C       D
        optC.setParentOption(optA);
        optD.setParentOption(optB);

        Set<Option> actual = optionService.getAllowableIncompatibleOptionsSet(optA.getId());
        Set<Option> expected = new HashSet<>(Arrays.asList(optB, optD));

        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));

        // Clear hierarchy
        optA.setParentOption(null);
        optB.setParentOption(null);
        optC.setParentOption(null);
        optD.setParentOption(null);

        // Prepare hierarchy
        //       A
        //       |
        //       B
        //     /   \
        //    C     D
        optB.setParentOption(optA);
        optC.setParentOption(optB);
        optD.setParentOption(optB);

        actual = optionService.getAllowableIncompatibleOptionsSet(optB.getId());

        assertTrue(actual.isEmpty());

    }

}
