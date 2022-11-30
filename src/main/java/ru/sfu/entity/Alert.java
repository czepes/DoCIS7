package ru.sfu.entity;

import java.io.Serializable;

/**
 * Alert Message Entity
 * @author Agapchenko V.V.
 */
public class Alert implements Serializable {
    /**
     * Alert Action
     */
    private String action;
    /**
     * Alert Body
     */
    private String body;

    /**
     * Default constructor
     */
    public Alert() {}

    /**
     * Constructor with parameters
     * @param action Alert Action
     * @param body Alert Body
     */
    public Alert(String action, String body) {
        super();
        this.action = action;
        this.body = body;
    }

    /**
     * Represent Alert Message as a String
     * @return String representing Alert
     */
    @Override
    public String toString() {
        return String.format("Alert[action=%s, body=%s]", action, body);
    }

    /**
     * Get Alert Action
     * @return Alert Action
     */
    public String getAction() {
        return action;
    }

    /**
     * Get Alert Body
     * @return Alert Body
     */
    public String getBody() {
        return body;
    }

    /**
     * Set Alert Action
     * @param action Alert Action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Set Alert Body
     * @param body Alert Body
     */
    public void setBody(String body) {
        this.body = body;
    }
}
