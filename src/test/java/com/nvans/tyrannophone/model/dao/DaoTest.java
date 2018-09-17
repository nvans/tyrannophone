package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = com.nvans.tyrannophone.config.JPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Commit
public class DaoTest {


    @Autowired private GenericDao<User> userDao;

    @Autowired private GenericDao<Role> roleDao;

    @Autowired private GenericDao<Plan> planDao;

    @Autowired private GenericDao<Details> detailsDao;

//    @Autowired private GenericDao<Contract> contractDao;
//
//    @Autowired private CustomerDao customerDao;


    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Before
    public void setUp() {

        assertNotNull(userDao);
        assertNotNull(roleDao);

    }

    @Test
    @Transactional
    public void testFindUserByID() {

        /*createRoles();
        createPlans();

        List<Role> roleList = roleDao.findAll();

        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.findById(1L));

        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setEmail("admin@tyrannophone.com");
        adminUser.setPassword(encoder.encode("admin"));
        adminUser.setRoles(roles);
        adminUser.setActive(true);

        userDao.create(adminUser);

        User custUsr = new User();
        custUsr.setUserName("cust1");
        custUsr.setEmail("cust1@customer.com");
        custUsr.setPassword(encoder.encode("cust1"));
        Set<Role> custRoles = custUsr.getRoles();
        custRoles.add(roleDao.findById(2L));
        custUsr.setRoles(custRoles);
        custUsr.setActive(true);

        userDao.create(custUsr);

        Employee employee = new Employee();
        employee.setUser(adminUser);
        employee.setFirstName("admin");
        employee.setLastName("admin");
        employee.setAddress("address");
        employee.setPassport("12343");
        employee.setPosition("employee");

        detailsDao.create(employee);


        Customer customer = new Customer();
        customer.setUser(custUsr);
        customer.setFirstName("John");
        customer.setLastName("Snow");
        customer.setAddress("1-st street");
        customer.setPassport("123456");
        customer.setBalance(0);

        detailsDao.create(customer);

        User cust1 = userDao.findByParam("userName", "cust1");
        User adm = userDao.findByParam("email", "admin1@tyrannophone.com");
        System.out.println(cust1.getBlockDetails());
        //List<User> list = userDao.findAllByParam("role", "ROLE_ADMIN");
        System.out.println();*/

    }

    /*@Test
    @Transactional
    public void testFindUserByEmail() {
        User
    }*/

    private void createRoles() {
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");

        Role customerRole = new Role();
        customerRole.setRole("ROLE_CUSTOMER");

        Role employeeRole = new Role();
        employeeRole.setRole("ROLE_EMPLOYEE");

        roleDao.create(adminRole);
        roleDao.create(customerRole);
        roleDao.create(employeeRole);
    }

    private void createPlans() {

        String[] planNames = { "Top", "Middle", "Bottom"};

        int initPrice = 600;

        for (String pName : planNames) {

            Plan plan = new Plan();
            plan.setPlanName(pName);
            plan.setConnectionPrice(0);
            plan.setMonthlyPrice(initPrice);
            plan.setConnectionAvailable(true);

            planDao.create(plan);

            initPrice -= 200;
        }

    }

//    private void createContracts() {
//
//        List<Plan> plans = planDao.findAll();
//        long initialContractNum = 7_700_000_01L;
//
//        for (Plan plan : plans) {
//            Contract contract1 = new Contract();
//            contract1.setContractNumber(initialContractNum++);
//            contract1.setPlan(plan);
//            contract1.setBlocked(false);
//
//            contractDao.create(contract1);
//        }
//
//    }


}
