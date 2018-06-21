package com.epam.horseraces.command.impl.manager;

import java.util.ResourceBundle;

/**
 * The {@code MessageManager} is applied for getting
 * the message which is needed using the string key
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class MessageManager {

    private static final String MESSAGES = "messages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(MESSAGES);

    /**
     * The constructor with no arguments
     */
    private MessageManager() {
    }

    /**
     * @param key The key which is used for getting
     *            the appropriate message
     * @return The appropriate message in string
     * format
     */
    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
