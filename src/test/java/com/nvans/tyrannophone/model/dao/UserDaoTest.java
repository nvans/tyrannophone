package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.Role;
import com.nvans.tyrannophone.model.entity.RoleType;
import com.nvans.tyrannophone.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = com.nvans.tyrannophone.config.JPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Commit
public class UserDaoTest {


    @Autowired
    private UserDao userDAO;

    private User testUser;

    @Before
    public void setUp() {

//        assertNotNull(userDAO);
//        assertNotNull(roleDAO);
//
//        Role role = new Role();
//        role.setRoleType(RoleType.ROLE_ADMIN);
//        roleDAO.save(role);
//        roleDAO.save(role);
//
//        testUser = new User();
//        testUser.setUserName("Vasya");
//        testUser.setEmail("vasya@vasya.va");
//        testUser.setPassword("admin");
//        testUser.setRole(roleDAO.findByType(RoleType.ROLE_ADMIN));
//        testUser.setActive(true);
//
//        userDAO.create(testUser);
    }

    @Test
//    @Transactional
    public void testFindUserByID() {
//
//        User foundUser = userDAO.findById(1L);
//
//        assertEquals(testUser, foundUser);
//
//        System.out.println(foundUser);
//
//        foundUser = userDAO.findByEmail(testUser.getEmail());
//
//        assertEquals(testUser, foundUser);
//
//        System.out.println(foundUser);
    }

    /*@Test
    @Transactional
    public void testFindUserByEmail() {
        User
    }*/


}
