package com.epam.horseraces.command.impl;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code EmptyCommand} class is applied when
 * it is an empty command
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class EmptyCommand implements ActionCommand {

    private static final String LOGIN_PAGE = "path.page.rightlogin";

    /**
     * @param request It's used for
     * @return The login page
     */
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty(LOGIN_PAGE);
    }
}
