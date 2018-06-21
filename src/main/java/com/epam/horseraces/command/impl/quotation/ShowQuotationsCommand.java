package com.epam.horseraces.command.impl.quotation;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.QuotationDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The {@code ShowQuotationsCommand} class is responsive for showing information
 * about {@code Quotation} objects.
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ShowQuotationsCommand implements ActionCommand {

    private static final String LIST = "list";
    private static final String USER_ID = "id";
    private static final String LOGGING_MESSAGE = "Showing bet information's impossible: ";

    private static final String COMMAND = "command";
    private static final String SHOW_BETS_HISTORY = "SHOW_BETS_HISTORY";
    private static final String SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION = "SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION";
    private static final String SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION = "SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION";
    private static final String DELETE_PREDICTION = "deleteprediction";
    private static final String SHOW_QUOTATIONS = "SHOW_QUOTATIONS";

    private static final String IS_FAILED_SHOW_BETS = "isFailedShowBets";
    private static final boolean FAILED = true;

    private static final String BOOKMAKER_PAGE = "path.page.bookmakerpage";
    private static final String SHOW_BETS_BEFORE_ADD_PAGE = "path.page.quotationbeforeadd";
    private static final String SHOW_BETS_BEFORE_EDIT_PAGE = "path.page.quotationbeforeedit";
    private static final String BETS_HISTORY_PAGE = "path.page.quotationhistorypage";
    private static final String PREDICTION_PAGE = "path.page.predictionpage";
    private static final String IS_CREATED = "created";
    private static final boolean CREATED = true;
    private static final boolean NOT_CREATED = false;

    private static Logger logger = LogManager.getLogger(ShowQuotationsCommand.class);

    /**
     * The method shows the list of existing horses and returns
     * the appropriate page
     *
     * @param request Contains attributes which are used for
     *                showing information about {@code Quotation} objects
     * @return The appropriate web page
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        String command = (String) session.getAttribute(COMMAND);

        try {

            setQuotationRequest(request);
            page = getCorrectPage(command);
            session.setAttribute(IS_CREATED, CREATED);

        } catch (DaoException e) {

            page = getErrorPage(command);
            logger.error(LOGGING_MESSAGE + e);
            setQuotationHistoryError(session, command);
            session.setAttribute(IS_CREATED, NOT_CREATED);

        }

        return page;

    }

    /**
     * The method sets the failed status to the showing history operation
     * if it's execution is impossible
     *
     * @param session It's applied for setting the error
     *                status of showing information of {@code Quotation}
     *                in current session
     * @param command Describes the command that was called
     *                by the users
     */
    private void setQuotationHistoryError(HttpSession session, String command) {

        if (SHOW_BETS_HISTORY.equals(command) || SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION.equals(command)) {
            session.setAttribute(IS_FAILED_SHOW_BETS, FAILED);
        }

    }

    /**
     * The method sets {@code Quotation} list for the subsequent
     * execution of show operation
     *
     * @param request It's applied for saving existing list of
     *                {@code Quotation} objects to display them on the appropriate
     *                web page
     * @throws DaoException If the list of {@code Quotation} objects is empty
     */
    private void setQuotationRequest(HttpServletRequest request) throws DaoException {

        HttpSession session = request.getSession();

        List<Quotation> quotations = getRecords(session);
        session.setAttribute(LIST, quotations);

    }

    /**
     * The method returns the list of {@code Prediction} records
     * using the information about the command which was
     * executed
     *
     * @param session It's used to get the appropriate list
     *                of {@code Quotation} objects
     * @return The list which contains the objects of
     * {@code Quotation} class
     * @throws DaoException If the list of {@code Quotation} objects
     *                      is empty
     */
    private List<Quotation> getRecords(HttpSession session) throws DaoException {

        String action = (String) session.getAttribute(COMMAND);
        Integer userId = (Integer) session.getAttribute(USER_ID);

        QuotationDaoImpl dao = new QuotationDaoImpl();

        List<Quotation> quotations;

        if (SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION.equals(action) || SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION.equals(action) || DELETE_PREDICTION.equals(action)) {
            quotations = dao.getAllRecords();
        } else if (SHOW_BETS_HISTORY.equals(action)) {
            quotations = dao.getHistoryRecords(userId);
        } else {
            quotations = dao.getRecordsById(userId);
        }

        return quotations;

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

        if (SHOW_QUOTATIONS.equals(command)) {
            page = ConfigurationManager.getProperty(BOOKMAKER_PAGE);
        } else if (SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_BETS_BEFORE_ADD_PAGE);
        } else if (SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION.equals(command)) {
            page = ConfigurationManager.getProperty(SHOW_BETS_BEFORE_EDIT_PAGE);
        } else if (SHOW_BETS_HISTORY.equals(command)) {
            page = ConfigurationManager.getProperty(BETS_HISTORY_PAGE);
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

        String page;

        if (SHOW_QUOTATIONS_BEFORE_ADD_PREDICTION.equals(command) || SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION.equals(command)) {
            page = ConfigurationManager.getProperty(PREDICTION_PAGE);
        } else {
            page = ConfigurationManager.getProperty(BOOKMAKER_PAGE);
        }

        return page;

    }
}
