package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

@ContextConfiguration(classes = com.nvans.tyrannophone.config.JPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OptionDaoTest {

    @Autowired
    OptionDao optionDao;

    private Option expectedOption;

    @Before
    public void setUp() {
        expectedOption = new Option();
        expectedOption.setName("Opt1");
        expectedOption.setPrice(0);
        expectedOption.setConnectionAvailable(true);

        optionDao.save(expectedOption);
    }

    @Test
    @Rollback
    public void testGetOptionById() {

        Option actualOption = optionDao.findById(expectedOption.getId());

        assertEquals(expectedOption, actualOption);
    }

    @Test
    @Rollback
    public void testGetOptionByName() {
        Option actualOption = optionDao.findByName(expectedOption.getName());

        assertEquals(expectedOption, actualOption);
    }

    @Test
    @Rollback
    public void testAddChildren() {

        Option parent = new Option();
        parent.setName("Parent");
        parent.setPrice(0);

        Option child1 = new Option();
        child1.setName("Child1");
        child1.setPrice(0);

        Option child2 = new Option();
        child2.setName("Child2");
        child2.setPrice(0);

        parent.getChildOptions().forEach(child -> assertEquals(parent, child.getParentOption()));



        optionDao.save(parent);

        Option actualParent = optionDao.findById(parent.getId());

        assertEquals(parent, actualParent);

    }


}
