package com.epam.horseraces.command.impl.horse;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code SetHorseParametersCommand} class is applied for
 * saving attributes in existing session that are needed for making edit
 * operations. Thereafter it sends to edit web page to finish edit operation
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class SetHorseParametersCommand implements ActionCommand {

    private static final String HORSE_ID = "id";
    private static final String HORSE_SESSION_ID = "horse_id";
    private static final String EDIT_HORSE_PAGE = "path.page.horsesedit";

    /**
     * The method executes saving attributes operation
     * and return the edit page
     *
     * @param request It's used for saving information about
     *                {@code Horse} object to make edit operation
     * @return The edit web page which is provide an
     * opportunity to edit {@code Horse} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(HORSE_SESSION_ID);
        session.setAttribute(HORSE_ID, id);

        return ConfigurationManager.getProperty(EDIT_HORSE_PAGE);

    }
}
