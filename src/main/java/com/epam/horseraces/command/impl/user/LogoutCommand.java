package com.epam.horseraces.command.impl.user;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code LogoutCommand} class is responsive for making logout
 * operation. If the logout command is used it sends to the login page
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class LogoutCommand implements ActionCommand {

    private static final String INDEX_PAGE = "path.page.index";

    /**
     * @param request It's used for making logout operation
     * @return The login page
     */
    public String execute(HttpServletRequest request) {

        String page = ConfigurationManager.getProperty(INDEX_PAGE);
        request.getSession().invalidate();

        return page;

    }
}
