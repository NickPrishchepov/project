package com.epam.horseraces.command.impl.horse;

import com.epam.horseraces.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code ActionHorseCommand} class is responsive for
 * making delete and edit operations on {@code Horse} objects. It defines
 * what kind of operation is needed and sends to the appropriate web page
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ActionHorseCommand implements ActionCommand {

    private static final String DELETE_COMMAND = "delete_command";
    private static final String EDIT_COMMAND = "edit_command";
    private static final String HORSE_ID = "horse_id";

    /**
     * The method sends to the edit or delete web page
     * according to the command which has been chosen by
     * users
     *
     * @param request Contains attributes which are taking part in
     *                delete and edit operations on {@code Horse} object
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
            session.setAttribute(HORSE_ID, recordId);

            DeleteHorseCommand deleteCommand = new DeleteHorseCommand();
            page = deleteCommand.execute(request);

        } else if (secondCommand != null) {

            Integer recordId = Integer.parseInt(secondCommand);
            session.setAttribute(HORSE_ID, recordId);

            SetHorseParametersCommand horseParametersCommand = new SetHorseParametersCommand();
            page = horseParametersCommand.execute(request);

        }

        return page;

    }
}
