package com.nvans.tyrannophone.config;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class JmsConfig {

    @Autowired
    private Environment env;

////    @Bean(initMethod = "start", destroyMethod = "stop")
//    public ActiveMQServer activeMQServer() throws Exception {
//
//        return ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
//                .setPersistenceEnabled(false)
//                .setJournalDirectory("target/data/journal")
//                .setSecurityEnabled(false)
//                .addAcceptorConfiguration("tcp", "tcp://localhost:61617")
//                .setFailoverOnServerShutdown(true));
//    }

    @Bean
    public ConnectionFactory connectionFactory() {

//        ActiveMQConnectionFactory connectionFactory =
//                new ActiveMQConnectionFactory("tcp://localhost:61616");
//        connectionFactory.setFailoverOnInitialConnection(true);

        Map<String, Object> props = new HashMap<>();
        props.put(TransportConstants.HOST_PROP_NAME, "192.168.99.100");
        props.put(TransportConstants.PORT_PROP_NAME, 61616);

        TransportConfiguration transportConfiguration =
                new TransportConfiguration(NettyConnectorFactory.class.getName(), props);


        ActiveMQConnectionFactory connectionFactory =
                ActiveMQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);
        connectionFactory.setUser("tyrannophone");
        connectionFactory.setPassword("tyrannophone");

        return connectionFactory;
    }



    @Bean
    public JmsTemplate jmsTemplate() throws NamingException {

        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setDefaultDestinationName("UpdateQueue");

        return jmsTemplate;
    }



}
