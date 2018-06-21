package com.epam.horseraces.command.impl.user;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.command.impl.race.ShowCommand;
import com.epam.horseraces.dao.entity.User;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServiceUserImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code Registration} class is responsive for making register
 * operation. It checks the correctness of the input data and thereafter
 * sends to the login page
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class Registration implements ActionCommand {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String E_MAIL = "e-mail";
    private static final String LOGIN_PAGE = "path.page.rightlogin";

    private static final String EXCEPTION_MESSAGE = "The user with such attributes has already exits.";
    private static final String LOGGING_MESSAGE = "The registration wasn't completed sucessfully: ";


    private static final String IS_FAILED_REGISTER = "isFailedRegister";
    private static final boolean FAILED = true;

    private static final int USER_ROLE_ID = 2;

    private static Logger logger = LogManager.getLogger(ShowCommand.class);

    /**
     * @param request Contains attributes that's necessary
     *                for registration
     * @return The login page
     */
    public String execute(HttpServletRequest request) {

        try {
            registerUser(request);
        } catch (DaoException e) {
            HttpSession session = request.getSession();
            session.setAttribute(IS_FAILED_REGISTER, FAILED);
            logger.error(LOGGING_MESSAGE + e);
        }

        return ConfigurationManager.getProperty(LOGIN_PAGE);
    }

    /**
     * @param request Contains attributes that's necessary
     *                for registration
     * @throws DaoException If an existing information of {@code User} object
     *                      is incorrect
     */
    private void registerUser(HttpServletRequest request) throws DaoException {

        String login = request.getParameter(LOGIN);

        ServiceUserImpl serviceUser = new ServiceUserImpl();
        User user = serviceUser.getUserByLogin(login);

        if (user == null) {
            addUser(serviceUser, request);
        } else {
            throw new DaoException(EXCEPTION_MESSAGE);
        }
    }

    /**
     * @param serviceUser The service that provides an opportunity to make
     *                    registration
     * @param request     Contains attributes that's necessary
     *                    for registration
     * @throws DaoException If an existing information of {@code User} object
     *                      is incorrect
     */
    private void addUser(ServiceUserImpl serviceUser, HttpServletRequest request) throws DaoException {

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(E_MAIL);

        serviceUser.addUser(USER_ROLE_ID, login, password, email);

    }
}
