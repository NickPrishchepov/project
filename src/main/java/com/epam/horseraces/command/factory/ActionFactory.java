package com.epam.horseraces.command.factory;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.CommandEnum;
import com.epam.horseraces.command.impl.EmptyCommand;
import com.epam.horseraces.command.impl.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code ActionFactory} class defines
 * what kind of operation is going to be used
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ActionFactory {

    private static final String COMMAND = "command";
    private static final String WRONG_ACTION = "wrongAction";
    private static final String WRONG_ACTION_MESSAGE = "message.wrongaction";

    /**
     * @param request It's used to define the command
     * @return {@code ActionCommand} object
     */
    public ActionCommand defineCommand(HttpServletRequest request) {

        ActionCommand current = new EmptyCommand();

        String action = request.getParameter(COMMAND);
        if (action == null || action.isEmpty()) {
            return current;
        }

        try {

            HttpSession session = request.getSession();
            session.setAttribute(COMMAND, action);

            String command = action.toUpperCase();
            CommandEnum currentEnum = CommandEnum.valueOf(command);
            current = currentEnum.getCurrentCommand();

        } catch (IllegalArgumentException e) {
            request.setAttribute(WRONG_ACTION, action + MessageManager.getProperty(WRONG_ACTION_MESSAGE));
        }

        return current;

    }
}
