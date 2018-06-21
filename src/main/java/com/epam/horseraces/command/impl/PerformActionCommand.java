package com.epam.horseraces.command.impl;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.user.LoginCommand;
import com.epam.horseraces.command.impl.user.Registration;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code PerformActionCommand} class is responsive for realizing
 * login or register command based on existing command
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class PerformActionCommand implements ActionCommand {

    private static final String REGISTER_COMMAND = "REGISTER";
    private static final String SIGN_IN_COMMAND = "SIGN_IN";
    private static final String ACTION = "action";

    /**
     * @param request It's applied for getting command which is needed for
     *                sign in or register operations
     * @return The login page or the appropriate web page
     * which is based on the existing command
     */
    public String execute(HttpServletRequest request) {

        String page = null;

        String command = request.getParameter(ACTION);
        String upperCaseCommand = command.toUpperCase();

        if (REGISTER_COMMAND.equals(upperCaseCommand)) {

            Registration registration = new Registration();
            page = registration.execute(request);

        } else if (SIGN_IN_COMMAND.equals(upperCaseCommand)) {

            LoginCommand loginCommand = new LoginCommand();
            page = loginCommand.execute(request);

        }

        return page;

    }
}
