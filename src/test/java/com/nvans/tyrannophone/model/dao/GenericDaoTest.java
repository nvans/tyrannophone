package com.nvans.tyrannophone.model.dao;

import com.nvans.tyrannophone.model.entity.BlockDetails;
import com.nvans.tyrannophone.model.entity.Role;
import com.nvans.tyrannophone.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@ContextConfiguration(classes = com.nvans.tyrannophone.config.JPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Commit
public class GenericDaoTest {

    @Autowired
    GenericDao<User> userDao;

    @Autowired
    GenericDao<BlockDetails> blockDetailsDao;

    @Test
    @Transactional
    public void testHz() {

//        User customer = userDao.findByParam("userName", "cust1");
//        BlockDetails bt = customer.getBlockDetails();
//        Set<Role> roles = customer.getRoles();


//        BlockDetails blockDetails = new BlockDetails();
//        blockDetails.setBlockedByUser(userDao.findById(1L));
//        blockDetails.setReason("blah-blah");
//        blockDetails.setBlockedUser(cust);
//        cust.setBlockDetails(blockDetails);


        System.out.println();
    }

}
