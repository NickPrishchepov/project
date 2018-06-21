package com.epam.horseraces.command.impl.race;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.entity.Race;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.RaceDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The {@code ShowCommand} class is responsive for showing information
 * about {@code Race} objects.
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ShowCommand implements ActionCommand {

    private static final String COMMAND = "command";
    private static final String SHOW_RACES_BEFORE_ADD_BET = "SHOW_RACES_BEFORE_ADD_BET";
    private static final String SET_BET_PARAMETERS = "SET_BET_PARAMETERS";
    private static final String SHOW_RACES_BEFORE_EDIT_BET = "SHOW_RACES_BEFORE_EDIT_BET";
    private static final String SHOW_RACES_USER_SIDE = "SHOW_RACES_USER_SIDE";
    private static final String SHOW_RACES_HISTORY = "SHOW_RACES_HISTORY";
    private static final String SHOW_RACES_BOOKMAKER_SIDE = "SHOW_RACES_BOOKMAKER_SIDE";
    private static final String SHOW_RACES = "SHOW_RACES";

    private static final String RACE_LIST = "race_list";
    private static final String RACE_PAGE = "path.page.racepage";
    private static final String SHOW_RACES_BEFORE_ADD_BET_PAGE = "path.page.racesbeforeadd";
    private static final String SHOW_RACES_BEFORE_EDIT_BET_PAGE = "path.page.racesbeforeedit";
    private static final String SHOW_RACES_BOOKMAKER_SIDE_PAGE = "path.page.races";
    private static final String SHOW_RACES_USER_SIDE_PAGE = "path.page.predraces";
    private static final String RACE_HISTORY_PAGE = "path.page.racehistorypage";
    private static final String BOOKMAKER_PAGE = "path.page.bookmakerpage";
    private static final String PREDICTION_PAGE = "path.page.predictionpage";
    private static final String USER_PAGE = "path.page.userpage";

    private static final String LOGGING_MESSAGE = "Showing race information's impossible: ";

    private static final String IS_FAILED_SHOW_RACES = "isFailedShowRaces";
    private static final boolean FAILED = true;

    private static final String IS_CREATED = "created";
    private static final boolean CREATED = true;
    private static final boolean NOT_CREATED = false;


    private static Logger logger = LogManager.getLogger(ShowCommand.class);

    /**
     * The method shows the list of existing horses and returns
     * the appropriate page
     *
     * @param request Contains attributes which are used for
     *                showing information about {@code Race} objects
     * @return The appropriate web page
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        String command = (String) session.getAttribute(COMMAND);

        try {

            setRaceRequest(request);
            page = getCorrectPage(command);
            session.setAttribute(IS_CREATED, CREATED);

        } catch (DaoException e) {

            session.setAttribute(IS_CREATED, NOT_CREATED);
            setRaceHistoryError(session, command);
            page = getErrorPage(command);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }

    /**
     * The method sets the failed status to the showing history operation
     * if it's execution is impossible
     *
     * @param session It's applied for setting the error
     *                status of showing information of {@code Race}
     *                in current session
     * @param command Describes the command that was called
     *                by the users
     */
    private void setRaceHistoryError(HttpSession session, String command) {

        if (SHOW_RACES_HISTORY.equals(command) || SHOW_RACES_BOOKMAKER_SIDE.equals(command) || SHOW_RACES_USER_SIDE.equals(command)) {
            session.setAttribute(IS_FAILED_SHOW_RACES, FAILED);
        }

    }

    /**
     * The method sets {@code Race} list for the subsequent
     * execution of show operation
     *
     * @param request It's applied for saving existing list of
     *                {@code Race} objects to display them on the appropriate
     *                web page
     * @throws DaoException If the list of {@code Race} objects is empty
     */
    private void setRaceRequest(HttpServletRequest request) throws DaoException {

        HttpSession session = request.getSession();

        List<Race> races = getRecords(session);

        session.setAttribute(RACE_LIST, races);

    }

    /**
     * The method returns the list of {@code Prediction} records
     * using the information about the command which was
     * executed
     *
     * @param session It's used to get the appropriate list
     *                of {@code Race} objects
     * @return The list which contains the objects of
     * {@code Race} class
     * @throws DaoException If the list of {@code Race} objects
     *                      is empty
     */
    private List<Race> getRecords(HttpSession session) throws DaoException {

        String action = (String) session.getAttribute(COMMAND);

        List<Race> races;
        RaceDaoImpl dao = new RaceDaoImpl();

        if (SHOW_RACES_HISTORY.equals(action)) {
            races = dao.getHistoryRecords();
        } else {
            races = dao.getAllRecords();
        }

        return races;

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

        if (SHOW_RACES.equals(command)) {
            page = ConfigurationManager.getProperty(RACE_PAGE);
        } else if (SHOW_RACES_BEFORE_ADD_BET.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_RACES_BEFORE_ADD_BET_PAGE);
        } else if (SHOW_RACES_BEFORE_EDIT_BET.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_RACES_BEFORE_EDIT_BET_PAGE);
        } else if (SHOW_RACES_BOOKMAKER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_RACES_BOOKMAKER_SIDE_PAGE);
        } else if (SHOW_RACES_USER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_RACES_USER_SIDE_PAGE);
        } else if (SHOW_RACES_HISTORY.equals(command)) {
            page = ConfigurationManager.getProperty(RACE_HISTORY_PAGE);
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

        if (SHOW_RACES.equals(command)) {
            page = ConfigurationManager.getProperty(RACE_PAGE);
        } else if (SHOW_RACES_BEFORE_ADD_BET.equals(command) || SET_BET_PARAMETERS.equals(command) || SHOW_RACES_BEFORE_EDIT_BET.equals(command) || SHOW_RACES_BOOKMAKER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(BOOKMAKER_PAGE);
        } else if (SHOW_RACES_USER_SIDE.equals(command)) {
            page = ConfigurationManager.getProperty(PREDICTION_PAGE);
        } else if (SHOW_RACES_HISTORY.equals(command)) {
            page = ConfigurationManager.getProperty(USER_PAGE);
        }

        return page;

    }
}

