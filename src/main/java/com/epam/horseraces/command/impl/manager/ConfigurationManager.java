package com.epam.horseraces.command.impl.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The {@code ConfigurationManager} is applied for getting
 * the message which is needed using the string key
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ConfigurationManager {

    private static final String CONFIG = "config";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(CONFIG, Locale.ENGLISH);

    /**
     * The constructor with no arguments
     */
    private ConfigurationManager() {
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
