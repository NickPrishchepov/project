package com.epam.horseraces.command.impl.user;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code SetUserParametersCommand} class is applied for
 * saving attributes in existing session that are needed for making edit
 * operations. Thereafter it sends to edit web page to finish edit operation
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class SetUserParametersCommand implements ActionCommand {

    private static final String USER_SESSION_ID = "user_id";
    private static final String USER_ID = "id";
    private static final String EDIT_USER_PAGE = "path.page.usersedit";

    /**
     * The method executes saving attributes operation
     * and return the edit page
     *
     * @param request It's used for saving information about
     *                {@code User} object to realize some operation
     *                on the object
     * @return The web page that provides edit operations
     * on {@code User} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(USER_SESSION_ID);
        session.setAttribute(USER_ID, id);

        return ConfigurationManager.getProperty(EDIT_USER_PAGE);

    }
}
