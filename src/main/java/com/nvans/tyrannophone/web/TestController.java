package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jms.JMSException;
import javax.naming.NamingException;

@Controller
public class TestController {

    @Autowired
    CustomerService customerService;

    @Autowired
    JmsTemplate jmsTemplate;


    @GetMapping("/test")
//    @Secured("ROLE_EMPLOYEE")
    public String testForm() throws JMSException, NamingException {

//        customerService.blockCustomer("cust1@tmail.tm");
//        customerService.unblockCustomer("cust1@tmail.tm");
//        CustomerDto customer = customerService.getCustomerByContractNumber(70002223344L);

//        jmsTemplate.convertAndSend("Test message");



//        Properties props = new Properties();
//        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
//        props.put(Context.PROVIDER_URL, "tcp://localhost:61617");
//        props.put("ConnectionFactory", "tcp://localhost:61617");
//        props.put("queue.queue/ExampleQueue", "ExampleQueue");
//        Context context = new InitialContext(props);
//        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
//        Queue queue = (Queue) context.lookup("queue/ExampleQueue");
//
//        Connection connection = connectionFactory.createConnection();
////        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue1 = session.createQueue("ExampleQueue");
//        MessageProducer producer = session.createProducer(queue1);
//        TextMessage message = session.createTextMessage("Hello sent at ");
//        System.out.println("Sending message: " + message.getText());
//        producer.send(message);
////        MessageConsumer messageConsumer = session.createConsumer(queue);
////        connection.start();
////        TextMessage messageReceived = (TextMessage) messageConsumer.receive(1000);
////        System.out.println("Received message:" + messageReceived.getText());
//        connection.close();

        jmsTemplate.convertAndSend("Hello from tyrannophone");

        return "test";
    }
}
