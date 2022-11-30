package ru.sfu.service;

import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Service;
import ru.sfu.entity.Alert;

/**
 * Alert Service
 * @author Agapchenko V.V.
 */
@Service
@PropertySource("classpath:jms.properties")
public class AlertServiceImpl implements AlertService {
    private final JmsTemplate jmsTemplate;

    /**
     * Constructor
     * @param jmsTemplate JMS Template
     */
    @Autowired
    public AlertServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Send Message to Queue using JMS Template
     * @param alert Alert Message
     */
    @Override
    public void sendAlert(Alert alert) {
        jmsTemplate.send(
                session -> session.createObjectMessage(alert)
        );
    }

    /**
     * Get Message from Queue using JMS Template
     * @return Alert Message
     */
    @Override
    public Alert getAlert() {
        try {
            ObjectMessage receivedMessage = (ObjectMessage) jmsTemplate.receive();
            return (Alert) receivedMessage.getObject();
        } catch (JMSException e) {
            throw JmsUtils.convertJmsAccessException(e);
        }
    }
}
