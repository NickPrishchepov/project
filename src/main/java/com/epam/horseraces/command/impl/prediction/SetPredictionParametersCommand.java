package com.epam.horseraces.command.impl.prediction;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.command.impl.quotation.ShowQuotationsCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code SetPredictionParametersCommand} class is applied for
 * saving attributes in existing session that are needed for making edit
 * operations. Thereafter it sends to edit web page to finish edit operation
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class SetPredictionParametersCommand implements ActionCommand {

    private static final String COMMAND = "command";
    private static final String ATTRIBUTE = "attribute";
    private static final String SET_QUOTATION_PARAMETERS_BEFORE_ADD_PREDICTION = "SET_QUOTATION_PARAMETERS_BEFORE_ADD_PREDICTION";
    private static final String PATH_PAGE_ADDPREDICTION = "path.page.addprediction";
    private static final String SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION = "SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION";
    private static final String SET_QUOTATION_PARAMETERS_BEFORE_EDIT_PREDICTION = "SET_QUOTATION_PARAMETERS_BEFORE_EDIT_PREDICTION";
    private static final String PATH_PAGE_EDITPREDICTION = "path.page.editprediction";
    private static final String QUOTATION_ID = "quotation_id";
    private static final String PREDICTION_ID = "prediction_id";

    /**
     * The method executes saving attributes operation
     * and return the appropriate page
     *
     * @param request It's used for saving information about
     *                {@code Prediction} object to realize some operation
     *                on the object
     * @return The web page which is corresponding to executed
     * command
     */
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String command = (String) session.getAttribute(COMMAND);

        Integer id;

        if (SET_QUOTATION_PARAMETERS_BEFORE_ADD_PREDICTION.equals(command) || SET_QUOTATION_PARAMETERS_BEFORE_EDIT_PREDICTION.equals(command)) {
            String stringId = request.getParameter(ATTRIBUTE);
            id = Integer.parseInt(stringId);
        } else {
            id = (Integer) session.getAttribute(ATTRIBUTE);
        }

        if (SET_QUOTATION_PARAMETERS_BEFORE_ADD_PREDICTION.equals(command) || SET_QUOTATION_PARAMETERS_BEFORE_EDIT_PREDICTION.equals(command)) {
            session.setAttribute(QUOTATION_ID, id);
        } else if (SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION.equals(command)) {
            session.setAttribute(PREDICTION_ID, id);
        }

        return getPage(command, request);

    }

    /**
     * The method returns the web page based on the
     * receiving command
     *
     * @param command Describes the command that was called
     *                by the users
     * @param request Provides an opportunity to realize showing
     *                information about object of {@code Quotation} class
     * @return The web page which is corresponding to executed
     * command
     */
    private String getPage(String command, HttpServletRequest request) {

        String page = null;

        if (SET_QUOTATION_PARAMETERS_BEFORE_ADD_PREDICTION.equals(command)) {
            page = ConfigurationManager.getProperty(PATH_PAGE_ADDPREDICTION);
        } else if (SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION.equals(command)) {
            ShowQuotationsCommand quotationsCommand = new ShowQuotationsCommand();
            page = quotationsCommand.execute(request);
        } else if (SET_QUOTATION_PARAMETERS_BEFORE_EDIT_PREDICTION.equals(command)) {
            page = ConfigurationManager.getProperty(PATH_PAGE_EDITPREDICTION);
        }

        return page;

    }
}
