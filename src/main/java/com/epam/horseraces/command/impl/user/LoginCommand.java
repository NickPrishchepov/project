package com.epam.horseraces.command.impl.user;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.command.impl.prediction.ShowPredictionsCommand;
import com.epam.horseraces.command.impl.quotation.ShowQuotationsCommand;
import com.epam.horseraces.dao.entity.User;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServiceUserImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code LoginCommand} class is responsive for making sign in
 * operation. It checks the correctness of the input data and thereafter
 * sends to the appropriate web page
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class LoginCommand implements ActionCommand {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String E_MAIL = "e-mail";
    private static final String USER_ID = "id";
    private static final String LOGIN_PAGE = "path.page.rightlogin";

    private static final String LOGGING_MESSAGE = "The sign in operation wasn't completed sucessfully: ";
    private static final String EXCEPTION_MESSAGE = "The user with such atrributes wasn't found.";

    private static final String IS_FAILED_LOGIN = "isFailedLogin";
    private static final boolean FAILED = true;

    private static final int ADMIN = 1;
    private static final int USER = 2;
    private static final int BOOKMAKER = 3;
    private static final String COMMAND = "command";
    private static final String SHOW_QUOTATIONS = "SHOW_QUOTATIONS";
    private static final String SHOW_PREDICTIONS = "SHOW_PREDICTIONS";


    private static Logger logger = LogManager.getLogger(LoginCommand.class);

    /**
     * @param request Contains attributes which are necessary
     *                for realizing sign in operation
     * @return The web page of administrator or the
     * web page of user or the web page of
     * bookmaker or the login page if
     * sign in operation was competed with errors
     */
    public String execute(HttpServletRequest request) {

        String page;

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(E_MAIL);

        User user;

        try {

            ServiceUserImpl serviceUser = new ServiceUserImpl();
            user = serviceUser.getUser(login, password, email);
            page = getPage(user, request);

        } catch (DaoException e) {

            HttpSession session = request.getSession();
            session.setAttribute(IS_FAILED_LOGIN, FAILED);
            page = ConfigurationManager.getProperty(LOGIN_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }

    /**
     * @param user    The {@code User} object that contains
     *                information which is needed for sign in
     * @param request It's used for making an appropriate show
     *                operations
     * @return The web page of administrator or the
     * web page of user or the web page of
     * bookmaker or the login page if
     * sign in operation was competed with errors
     * @throws DaoException If the input data was incorrect
     */
    private String getPage(User user, HttpServletRequest request) throws DaoException {

        String page;

        if (user != null) {
            page = getAppropriatePage(user, request);
        } else {
            throw new DaoException(EXCEPTION_MESSAGE);
        }

        return page;

    }

    /**
     * @param user    The {@code User} object that contains
     *                information which is needed for sign in
     * @param request It's used for making an appropriate show
     *                operations
     * @return The web page of administrator or the
     * web page of user or the web page of
     * bookmaker or the login page if
     * sign in operation was competed with errors
     */
    private String getAppropriatePage(User user, HttpServletRequest request) {

        String page = null;

        Integer userId = user.getId();
        Integer roleId = user.getRoleId();

        HttpSession session = request.getSession();
        session.setAttribute(USER_ID, userId);

        if (roleId == ADMIN) {

            com.epam.horseraces.command.impl.user.ShowCommand showCommand = new com.epam.horseraces.command.impl.user.ShowCommand();
            page = showCommand.execute(request);

        } else if (roleId == USER) {

            ShowPredictionsCommand showPredictionsCommand = new ShowPredictionsCommand();
            session.setAttribute(COMMAND, SHOW_PREDICTIONS);
            page = showPredictionsCommand.execute(request);

        } else if (roleId == BOOKMAKER) {

            ShowQuotationsCommand showCommand = new ShowQuotationsCommand();
            session.setAttribute(COMMAND, SHOW_QUOTATIONS);
            page = showCommand.execute(request);

        }

        return page;

    }

}
