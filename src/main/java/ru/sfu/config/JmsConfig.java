package ru.sfu.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Java Message Service Configuration
 * @author Agapchenko V.V.
 */
@Configuration
@EnableJms
@PropertySource("classpath:jms.properties")
public class JmsConfig {

    private final Environment env;

    /**
     * Constructor
     * @param env Environment
     */
    @Autowired
    public JmsConfig(Environment env) {
        this.env = env;
    }

    /**
     * Initialize Queue
     * @return ActiveMQ Queue
     */
    @Bean
    public Queue televisionQueue() {
        return ActiveMQQueue.createQueue(env.getProperty("queue.television-queue"));
    }

    /**
     * Initialize Connection Factory
     * @return ActiveMQ Connection Factory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(env.getProperty("broker.url"));
    }

    /**
     * Initialize JMS Template
     * @return JMS Template
     */
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setDefaultDestination(televisionQueue());
        return jmsTemplate;
    }

    /**
     * Initialize JMS Listener Container Factory
     * @return Default JMS Listener Container Factory
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }
}
