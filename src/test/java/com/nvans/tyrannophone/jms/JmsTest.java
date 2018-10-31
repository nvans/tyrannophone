package com.nvans.tyrannophone.jms;

import org.apache.log4j.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


public class JmsTest {

    private static final Logger log = Logger.getLogger(JmsTest.class);

    private static final String DEFAULT_MESSAGE = "Hello from Tyrannophone!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/tyrannophone";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String DEFAULT_USERNAME = "tyrannophone";
    private static final String DEFAULT_PASSWORD = "tyrannophone";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:8180";

    public static void main(String[] args) {

        Context namingContext = null;

        try {

            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL,PROVIDER_URL);
            env.put(Context.SECURITY_PRINCIPAL, DEFAULT_USERNAME);
            env.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD);
            namingContext = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = DEFAULT_CONNECTION_FACTORY;
            log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = DEFAULT_DESTINATION;
            log.info("Attempting to acquire destination \"" + destinationString + "\"");
            Destination destination = (Destination) namingContext.lookup(destinationString);
            log.info("Found destination \"" + destinationString + "\" in JNDI");

            int count = Integer.parseInt(DEFAULT_MESSAGE_COUNT);
            String content = DEFAULT_MESSAGE;

            try (JMSContext context = connectionFactory.createContext(DEFAULT_USERNAME, DEFAULT_PASSWORD)) {
                log.info("Sending " + count + " messages with content: " + content);
                // Send the specified number of messages
                for (int i = 0; i < count; i++) {
                    context.createProducer().send(destination, content);
                }

                // Create the JMS consumer
//                JMSConsumer consumer = context.createConsumer(destination);
//                // Then receive the same number of messages that were sent
//                for (int i = 0; i < count; i++) {
//                    String text = consumer.receiveBody(String.class, 5000);
//                    log.info("Received message with content " + text);
//                }
            }
        } catch (NamingException e) {
            log.warn(e.getMessage());
            e.printStackTrace();
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException e) {
                    log.warn(e.getMessage());
                }
            }
        }

    }

}
