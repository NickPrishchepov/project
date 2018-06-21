package com.epam.horseraces.command.impl.user;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServiceUserImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code EditCommand} class is responsive for
 * making edit operations on {@code User} objects. It receives an attribute that
 * describes the unique identifier of the {@code User} object. It calls
 * method that receives the identifier and then edit the corresponding record
 * in existing database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class EditCommand implements ActionCommand {

    private static final String USER_ID = "id";
    private static final String ROLE_ID = "role";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String E_MAIL = "e-mail";
    private static final String USER_PAGE = "path.page.userpage";

    private static final String IS_FAILED_EDIT_USER = "isFailedEditUser";
    private static final boolean FAILED = true;

    private static final String LOGGING_MESSAGE = "The edition of user wasn't finished successfully: ";

    private static Logger logger = LogManager.getLogger(EditCommand.class);

    /**
     * The method execute edit operation calling the service methods
     * and return the main user page
     *
     * @param request Contains attributes that are necessary for
     *                making edit operations
     * @return The main user page which is providing
     * delete and edit operations on {@code User} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(USER_ID);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(E_MAIL);
        String stringRole = request.getParameter(ROLE_ID);

        try {

            ServiceUserImpl serviceUser = new ServiceUserImpl();
            Integer roleId = serviceUser.getRoleId(stringRole);
            serviceUser.editUser(id, roleId, login, password, email);

            ShowCommand showCommand = new ShowCommand();
            page = showCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_EDIT_USER, FAILED);
            page = ConfigurationManager.getProperty(USER_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }

}
