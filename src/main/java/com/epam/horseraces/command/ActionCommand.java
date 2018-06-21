package com.epam.horseraces.command;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code ActionCommand} interface is applied for
 * for setting the execute method that main command's
 * classes should implement
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public interface ActionCommand {
    /**
     * @param request It's applied for setting the attributes
     *                which's needed for realizing some operation
     * @return The appropriate web page
     */
    String execute(HttpServletRequest request);
}
