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
 * The {@code DeleteCommand} class is responsive for
 * making delete operations on {@code User} objects. It receives an attribute that
 * describes the unique identifier of the {@code User} object. It calls
 * method that receives existing identifier and then delete the corresponding record
 * from database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class DeleteCommand implements ActionCommand {

    private static final String USER_ID = "user_id";
    private static final String USER_PAGE = "path.page.userpage";
    private static final String LOGGING_MESSAGE = "The deleting of user wasn't finished successfully: ";

    private static final String IS_FAILED_DELETE_USER = "isFailedDeleteUser";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(DeleteCommand.class);

    /**
     * The method execute delete operation calling the service methods
     * and return the main user page
     *
     * @param request Contains attributes that are necessary for
     *                making delete operations
     * @return The main user page which is providing
     * delete and edit operations on {@code User} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(USER_ID);

        try {

            ServiceUserImpl serviceUser = new ServiceUserImpl();
            serviceUser.deleteUser(id);

            ShowCommand showCommand = new ShowCommand();
            page = showCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_DELETE_USER, FAILED);
            page = ConfigurationManager.getProperty(USER_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }
}
