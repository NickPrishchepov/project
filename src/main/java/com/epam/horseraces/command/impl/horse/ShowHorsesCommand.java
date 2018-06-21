package com.epam.horseraces.command.impl.horse;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.entity.Horse;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.HorseDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The {@code ShowHorsesCommand} class is responsive for showing information
 * about {@code Horse} objects.
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ShowHorsesCommand implements ActionCommand {

    private static final String COMMAND = "command";
    private static final String SET_RACES_PARAMETERS_BEFORE_ADD_BET = "SET_RACES_PARAMETERS_BEFORE_ADD_BET";
    private static final String SET_RACES_PARAMETERS_BEFORE_EDIT_BET = "SET_RACES_PARAMETERS_BEFORE_EDIT_BET";
    private static final String SHOW_HORSES_USER_SIDE = "SHOW_HORSES_USER_SIDE";
    private static final String SHOW_HORSES = "SHOW_HORSES";
    private static final String SHOW_HORSES_BOOKMAKER_SIDE = "SHOW_HORSES_BOOKMAKER_SIDE";
    private static final String HORSES_LIST = "horses";
    private static final String LOGGING_MESSAGE = "Showing horses information's impossible: ";
    private static final String HORSE_PAGE = "path.page.horsepage";
    private static final String BOOKMAKER_PAGE = "path.page.bookmakerpage";
    private static final String PREDICTION_PAGE = "path.page.predictionpage";
    private static final String HORSES_BEFORE_ADD_BET_PAGE = "path.page.horsesbeforeadd";
    private static final String HORSES_BEFORE_EDIT_BET_PAGE = "path.page.horsesbeforeedit";
    private static final String SHOW_HORSES_BOOKMAKER_SIDE_PAGE = "path.page.horses";
    private static final String SHOW_HORSES_USER_SIDE_PAGE = "path.page.predhorses";
    private static final String IS_CREATED = "isCreated";
    private static final String IS_FAILED_SHOW_HORSES = "isFailedShowHorses";
    private static final boolean FAILED = true;
    private static final boolean CREATED = true;
    private static final boolean NOT_CREATED = false;

    private static Logger logger = LogManager.getLogger(ShowHorsesCommand.class);

    /**
     * The method shows the list of existing horses and returns
     * the appropriate page
     *
     * @param request Contains attributes which are used for
     *                showing information about {@code Horses} objects
     * @return The appropriate web page
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        String command = (String) session.getAttribute(COMMAND);

        System.out.print(command);

        try {

            setHorseRequest(request);
            page = getCorrectPage(command);
            session.setAttribute(IS_CREATED, CREATED);

        } catch (DaoException e) {

            setHorseError(session, command);
            session.setAttribute(IS_CREATED, NOT_CREATED);
            page = getErrorPage(command);
            logger.error(LOGGING_MESSAGE + e);
        }

        return page;

    }


    /**
     * The method sets the failed status to the showing operation
     * if it's execution is impossible
     *
     * @param session It's applied for setting the error
     *                status of showing information of {@code Horses}
     *                in current session
     * @param command Describes the command that was called
     *                by the users
     */
    private void setHorseError(HttpSession session, String command) {

        if (SHOW_HORSES_BOOKMAKER_SIDE.equals(command) || SHOW_HORSES_USER_SIDE.equals(command)) {
            session.setAttribute(IS_FAILED_SHOW_HORSES, FAILED);
        }

    }

    /**
     * The method sets {@code Horse} list for the subsequent
     * execution of show operation
     *
     * @param request It's applied for saving existing list of
     *                {@code Horse} objects to display them on the appropriate
     *                web page
     * @throws DaoException If the list of {@code Horse} objects is empty
     */
    private void setHorseRequest(HttpServletRequest request) throws DaoException {

        HorseDaoImpl dao = new HorseDaoImpl();
        List<Horse> horses = dao.getAllRecords();

        HttpSession session = request.getSession();
        session.setAttribute(HORSES_LIST, horses);

    }

    /**
     * The method returns the appropriate web page
     * if the execution of command was successful
     *
     * @param command Describes the command that was called
     *                by the users
     * @return The web page which is corresponding to
     * the used command if command was executed
     * with success
     */
    private String getCorrectPage(String command) {

        String page = null;

        if (SHOW_HORSES.equals(command)) {
            page = ConfigurationManager.getProperty(HORSE_PAGE);
        } else if (SET_RACES_PARAMETERS_BEFORE_ADD_BET.equals(command)) {
            page = ConfigurationManager.getProperty(HORSES_BEFORE_ADD_BET_PAGE);
        } else if (SET_RACES_PARAMETERS_BEFORE_EDIT_BET.equals(command)) {
            page = ConfigurationManager.getProperty(HORSES_BEFORE_EDIT_BET_PAGE);
        } else if (SHOW_HORSES_BOOKMAKER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_HORSES_BOOKMAKER_SIDE_PAGE);
        } else if (SHOW_HORSES_USER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_HORSES_USER_SIDE_PAGE);
        }
        return page;

    }

    /**
     * The method returns the appropriate web page
     * if the execution of command was competed
     * with errors
     *
     * @param command Describes the command that was called
     *                by the users
     * @return The web page which is corresponding to
     * the used command if command was competed
     * with errors
     */
    private String getErrorPage(String command) {

        String page = null;

        if (SET_RACES_PARAMETERS_BEFORE_ADD_BET.equals(command) || SET_RACES_PARAMETERS_BEFORE_EDIT_BET.equals(command)) {
            page = ConfigurationManager.getProperty(BOOKMAKER_PAGE);
        } else if (SHOW_HORSES_BOOKMAKER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(BOOKMAKER_PAGE);
        } else if (SHOW_HORSES.equals(command)) {
            page = ConfigurationManager.getProperty(HORSE_PAGE);
        } else if (SHOW_HORSES_USER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(PREDICTION_PAGE);
        }

        return page;

    }
}
