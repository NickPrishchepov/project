package com.epam.horseraces.command.impl.prediction;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.entity.Prediction;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.PredictionDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The {@code ShowPredictionsCommand} class is responsive for showing information
 * about {@code Prediction} objects.
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ShowPredictionsCommand implements ActionCommand {

    private static final String LIST = "list";
    private static final String ID = "id";
    private static final String PREDICTION_PAGE = "path.page.predictionpage";
    private static final String PREDICTION_HISTORY_PAGE = "path.page.predictionhistorypage";
    private static final String SHOWING_PREDICTION_INFORMATION_S_IMPOSSIBLE = "Showing prediction information's impossible: ";
    private static final String COMMAND = "command";
    private static final String IS_CREATED = "created";
    private static final boolean CREATED = true;
    private static final boolean NOT_CREATED = false;
    private static final String SHOW_PREDICTIONS_HISTORY = "SHOW_PREDICTIONS_HISTORY";

    private static final String IS_FAILED_SHOW_PREDICTIONS = "isFailedShowPredictions";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(ShowPredictionsCommand.class);

    /**
     * The method shows the list of existing horses and returns
     * the appropriate page
     *
     * @param request Contains attributes which are used for
     *                showing information about {@code Prediction} objects
     * @return The appropriate web page
     */
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String command = (String) session.getAttribute(COMMAND);

        String page;

        try {

            setPredictionRequest(request);
            page = getCorrect(command);
            session.setAttribute(IS_CREATED, CREATED);

        } catch (DaoException e) {

            logger.error(SHOWING_PREDICTION_INFORMATION_S_IMPOSSIBLE + e);
            setPredictionHistoryError(session, command);
            page = getErrorPage(command, request);
            session.setAttribute(IS_CREATED, NOT_CREATED);


        }

        return page;
    }

    /**
     * The method sets the failed status to the showing history operation
     * if it's execution is impossible
     *
     * @param session It's applied for setting the error
     *                status of showing information of {@code Prediction}
     *                in current session
     * @param command Describes the command that was called
     *                by the users
     */
    private void setPredictionHistoryError(HttpSession session, String command) {

        if (SHOW_PREDICTIONS_HISTORY.equals(command)) {
            session.setAttribute(IS_FAILED_SHOW_PREDICTIONS, FAILED);
        }

    }

    /**
     * The method sets {@code Prediction} list for the subsequent
     * execution of show operation
     *
     * @param request It's applied for saving existing list of
     *                {@code Prediction} objects to display them on the appropriate
     *                web page
     * @throws DaoException If the list of {@code Prediction} objects is empty
     */
    private void setPredictionRequest(HttpServletRequest request) throws DaoException {

        HttpSession session = request.getSession();
        List<Prediction> predictions = getRecords(session);
        session.setAttribute(LIST, predictions);

    }


    /**
     * The method returns the list of {@code Prediction} records
     * using the information about the command which was
     * executed
     *
     * @param session It's used to get the appropriate list
     *                of {@code Prediction} objects
     * @return The list which contains the objects of
     * {@code Prediction} class
     * @throws DaoException If the list of {@code Prediction} objects
     *                      is empty
     */
    private List<Prediction> getRecords(HttpSession session) throws DaoException {

        String action = (String) session.getAttribute(COMMAND);
        Integer userId = (Integer) session.getAttribute(ID);

        PredictionDaoImpl dao = new PredictionDaoImpl();
        List<Prediction> predictions;

        if (SHOW_PREDICTIONS_HISTORY.equals(action)) {
            predictions = dao.getHistoryRecords(userId);
        } else {
            predictions = dao.getRecordsById(userId);
        }
        return predictions;
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
    private String getCorrect(String command) {

        String page;

        if (SHOW_PREDICTIONS_HISTORY.equals(command)) {
            page = ConfigurationManager.getProperty(PREDICTION_HISTORY_PAGE);
        } else {
            page = ConfigurationManager.getProperty(PREDICTION_PAGE);
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
    private String getErrorPage(String command, HttpServletRequest request) {

        if (SHOW_PREDICTIONS_HISTORY.equals(command)) {
            HttpSession session = request.getSession();
            //  session.setAttribute(IS_FAILED_SHOW_PREDICTIONS, FAILED);
        }

        return ConfigurationManager.getProperty(PREDICTION_PAGE);

    }

}
