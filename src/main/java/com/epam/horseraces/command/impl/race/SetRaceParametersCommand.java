package com.epam.horseraces.command.impl.race;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code SetRaceParametersCommand} class is applied for
 * saving attributes in existing session that are needed for making edit
 * operations. Thereafter it sends to edit web page to finish edit operation
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class SetRaceParametersCommand implements ActionCommand {

    private static final String RACE_SESSION_ID = "race_id";
    private static final String RACE_ID = "id";
    private static final String EDIT_PAGE = "path.page.racesedit";

    /**
     * The method executes saving attributes operation
     * and return the edit page
     *
     * @param request It's used for saving information about
     *                {@code Race} object to realize some operation
     *                on the object
     * @return The web page that provides edit operations
     * on {@code Race} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(RACE_SESSION_ID);
        session.setAttribute(RACE_ID, id);

        return ConfigurationManager.getProperty(EDIT_PAGE);

    }
}
