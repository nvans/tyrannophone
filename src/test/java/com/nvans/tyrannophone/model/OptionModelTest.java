package com.nvans.tyrannophone.model;

import com.nvans.tyrannophone.model.entity.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class OptionModelTest {

    private Option parent1, parent2, child1, child2;

    @Before
    public void init() {
        parent1 = new Option();
        parent1.setName("Parent1");
        parent1.setPrice(0);

        parent2 = new Option();
        parent1.setName("Parent2");
        parent1.setPrice(0);

        child1 = new Option();
        child1.setName("Child1");
        child1.setPrice(0);

        child2 = new Option();
        child2.setName("Child2");
        child2.setPrice(0);

    }

    @Test
    public void testAddChildren() {

        Set<Option> children = new HashSet<>(Arrays.asList(child1, child2));
        parent1.setChildOptions(children);

        assertEquals(children, parent1.getChildOptions());

        parent1.getChildOptions().forEach(
                child -> assertEquals(parent1, child.getParentOption()));

    }

    @Test
    public void testAddParent() {

        assertNull(child1.getParentOption());

        child1.setParentOption(parent1);

        assertEquals(parent1, child1.getParentOption());
        assertTrue(parent1.getChildOptions().contains(child1));
    }

}
