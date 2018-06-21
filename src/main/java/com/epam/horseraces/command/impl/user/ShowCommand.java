package com.epam.horseraces.command.impl.user;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.entity.User;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.UserDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The {@code ShowCommand} class is responsive for showing information
 * about {@code User} objects.
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ShowCommand implements ActionCommand {

    private static final String USER_LIST = "users";
    private static final String USER_PAGE = "path.page.userpage";
    private static final String LOGGING_MESSAGE = "Showing users information's impossible: ";

    private static Logger logger = LogManager.getLogger(com.epam.horseraces.command.impl.race.ShowCommand.class);

    /**
     * The method shows the list of existing horses and returns
     * the user page
     *
     * @param request Contains attributes which are used for
     *                showing information about {@code User} objects
     * @return The main user page that provides delete
     * and edit operations
     */
    @Override
    public String execute(HttpServletRequest request) {

        try {
            setUserRequest(request);
        } catch (DaoException e) {
            logger.error(LOGGING_MESSAGE + e);
        }

        return ConfigurationManager.getProperty(USER_PAGE);
    }


    /**
     * The method sets {@code User} list for the subsequent
     * execution of show operation
     *
     * @param request It's applied for saving existing list of
     *                {@code User} objects to display them on the appropriate
     *                web page
     * @throws DaoException If the list of {@code User} objects is empty
     */
    private void setUserRequest(HttpServletRequest request) throws DaoException {

        UserDaoImpl dao = new UserDaoImpl();
        List<User> users = dao.getAllRecords();

        HttpSession session = request.getSession();
        session.setAttribute(USER_LIST, users);

    }

}
