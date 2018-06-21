package com.epam.horseraces.command.impl.prediction;

import com.epam.horseraces.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code ActionPredictionCommand} class is responsive for
 * making delete and edit operations on {@code Prediction} objects. It defines
 * what kind of operation is needed and sends to the appropriate web page
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ActionPredictionCommand implements ActionCommand {

    private static final String DELETE_COMMAND = "delete";
    private static final String EDIT_COMMAND = "edit";
    private static final String RECORD_ID = "attribute";
    private static final String COMMAND = "command";
    private static final String SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION = "SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION";


    /**
     * The method sends to the edit or delete web page
     * according to the command which has been chosen by
     * users
     *
     * @param request Contains attributes which are taking part in
     *                delete and edit operations on {@code Prediction} object
     * @return The web page that is responsive for
     * delete or edit operations
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page = null;

        String firstCommand = request.getParameter(DELETE_COMMAND);
        String secondCommand = request.getParameter(EDIT_COMMAND);

        HttpSession session = request.getSession();

        if (firstCommand != null) {

            Integer recordId = Integer.parseInt(firstCommand);
            session.setAttribute(RECORD_ID, recordId);

            DeletePredictionCommand deleteCommand = new DeletePredictionCommand();
            page = deleteCommand.execute(request);

        } else if (secondCommand != null) {

            Integer recordId = Integer.parseInt(secondCommand);
            session.setAttribute(RECORD_ID, recordId);
            session.setAttribute(COMMAND, SHOW_QUOTATIONS_BEFORE_EDIT_PREDICTION);

            SetPredictionParametersCommand predictionParametersCommand = new SetPredictionParametersCommand();
            page = predictionParametersCommand.execute(request);

        }

        return page;

    }
}
