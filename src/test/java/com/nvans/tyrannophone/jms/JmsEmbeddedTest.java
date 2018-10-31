package com.nvans.tyrannophone.jms;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {com.nvans.tyrannophone.config.JmsConfig.class
                                 })
@RunWith(SpringJUnit4ClassRunner.class)
public class JmsEmbeddedTest {


    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testSendAndConsume() throws Exception {

//        jmsTemplate.convertAndSend("Test Message");
//
//        Message m = jmsTemplate.receive("tyrannophone");
//
//        System.out.println(m);
    }
}
