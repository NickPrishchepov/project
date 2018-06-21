package com.epam.horseraces.command.impl.quotation;

import com.epam.horseraces.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code ActionQuotationCommand} class is responsive for
 * making delete and edit operations on {@code Quotation} objects. It defines
 * what kind of operation is needed and sends to the appropriate web page
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ActionQuotationCommand implements ActionCommand {

    private static final String DELETE_COMMAND = "delete_command";
    private static final String EDIT_COMMAND = "edit_command";
    private static final String RECORD_ID = "attribute";
    private static final String COMMAND = "command";
    private static final String SHOW_RACES_BEFORE_EDIT_BET = "SHOW_RACES_BEFORE_EDIT_BET";

    /**
     * The method sends to the edit or delete web page
     * according to the command which has been chosen by
     * users
     *
     * @param request Contains attributes which are taking part in
     *                delete and edit operations on {@code Quotation} object
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

            DeleleBetCommand deleteCommand = new DeleleBetCommand();
            page = deleteCommand.execute(request);

        } else if (secondCommand != null) {

            Integer recordId = Integer.parseInt(secondCommand);
            session.setAttribute(RECORD_ID, recordId);
            session.setAttribute(COMMAND, SHOW_RACES_BEFORE_EDIT_BET);

            SetQuotationParametersCommand quotationParametersCommand = new SetQuotationParametersCommand();
            page = quotationParametersCommand.execute(request);

        }

        return page;

    }
}
