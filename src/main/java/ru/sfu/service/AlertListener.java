package ru.sfu.service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.sfu.entity.Alert;

/**
 * Java Message Service Listener
 * @author Agapchenko V.V.
 */
@Component
@PropertySource("classpath:jms.properties")
public class AlertListener {

    /**
     * Get + process message from Queue
     * @param alert Message from Queue
     */
    @JmsListener(destination = "${queue.television-queue}")
    public void listenMethod(Alert alert) {
        System.out.println(alert);
    }
}
