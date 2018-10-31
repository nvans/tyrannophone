package com.nvans.tyrannophone.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotifyShowcaseAspect {

    private static final Logger log = Logger.getLogger(NotifyShowcaseAspect.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @AfterReturning("@annotation(NotifyShowcase)")
    public void notifyShowcase() {

        log.info("Send notification to showcase.");

        try {
            jmsTemplate.convertAndSend("It's time to update!");
            log.info("Message sent");
        }
        catch (Exception ex) {
            log.warn("Can't send message. " + ex.getMessage());
        }
    }

}
