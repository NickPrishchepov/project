package com.epam.horseraces.command.impl.quotation;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.horse.ShowHorsesCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.command.impl.race.ShowCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code SetQuotationParametersCommand} class is applied for
 * saving attributes in existing session that are needed for making edit
 * operations. Thereafter it sends to edit web page to finish edit operation
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class SetQuotationParametersCommand implements ActionCommand {

    private static final String COMMAND = "command";
    private static final String RECORD_ID = "attribute";
    private static final String HORSE_ID = "horse_id";
    private static final String RACE_ID = "race_id";
    private static final String SHOW_RACES_BEFORE_EDIT_BET = "SHOW_RACES_BEFORE_EDIT_BET";
    private static final String QUOTATION_ID = "quotation_id";
    private static final String ADD_BET_PAGE = "path.page.addbet";
    private static final String EDIT_BET_PAGE = "path.page.editbet";
    private static final String SET_RACES_PARAMETERS_BEFORE_ADD_BET = "SET_RACES_PARAMETERS_BEFORE_ADD_BET";
    private static final String SET_HORSE_PARAMETERS_BEFORE_ADD_BET = "SET_HORSE_PARAMETERS_BEFORE_ADD_BET";
    private static final String SET_RACES_PARAMETERS_BEFORE_EDIT_BET = "SET_RACES_PARAMETERS_BEFORE_EDIT_BET";
    private static final String SET_HORSE_PARAMETERS_BEFORE_EDIT_BET = "SET_HORSE_PARAMETERS_BEFORE_EDIT_BET";
    private static final String EBET = "ebet";

    /**
     * The method executes saving attributes operation
     * and return the appropriate page
     *
     * @param request It's used for saving information about
     *                {@code Quotation} object to realize some operation
     *                on the object
     * @return The web page which is corresponding to executed
     * command
     */
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String command = (String) session.getAttribute(COMMAND);

        System.out.print(command);

        //SHOW_RACES_BEFORE_EDIT_BET.equals(command)

        Integer id;

        if (SET_RACES_PARAMETERS_BEFORE_ADD_BET.equals(command) || SET_HORSE_PARAMETERS_BEFORE_ADD_BET.equals(command) || SET_RACES_PARAMETERS_BEFORE_EDIT_BET.equals(command) || SET_HORSE_PARAMETERS_BEFORE_EDIT_BET.equals(command) || EBET.equals(command)) {
            String stringId = request.getParameter(RECORD_ID);
            id = Integer.parseInt(stringId);
        } else {
            id = (Integer) session.getAttribute(RECORD_ID);
        }

        if (SET_HORSE_PARAMETERS_BEFORE_ADD_BET.equals(command) || SET_HORSE_PARAMETERS_BEFORE_EDIT_BET.equals(command) || EBET.equals(command)) {
            session.setAttribute(HORSE_ID, id);

        } else if (SET_RACES_PARAMETERS_BEFORE_ADD_BET.equals(command) || SET_RACES_PARAMETERS_BEFORE_EDIT_BET.equals(command)) {
            session.setAttribute(RACE_ID, id);
        } else if (SHOW_RACES_BEFORE_EDIT_BET.equals(command)) {
            session.setAttribute(QUOTATION_ID, id);
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

        if (SET_RACES_PARAMETERS_BEFORE_ADD_BET.equals(command)) {
            ShowHorsesCommand horsesCommand = new ShowHorsesCommand();
            page = horsesCommand.execute(request);
        } else if (SET_HORSE_PARAMETERS_BEFORE_ADD_BET.equals(command)) {
            page = ConfigurationManager.getProperty(ADD_BET_PAGE);
        } else if (SHOW_RACES_BEFORE_EDIT_BET.equals(command)) {
            ShowCommand showCommand = new ShowCommand();
            page = showCommand.execute(request);
        } else if (EBET.equals(command)) {
            page = ConfigurationManager.getProperty(EDIT_BET_PAGE);
        } else if (SET_RACES_PARAMETERS_BEFORE_EDIT_BET.equals(command)) {
            ShowHorsesCommand horsesCommand = new ShowHorsesCommand();
            page = horsesCommand.execute(request);
        } else if (SET_HORSE_PARAMETERS_BEFORE_EDIT_BET.equals(command)) {
            page = ConfigurationManager.getProperty(EDIT_BET_PAGE);
        }
        return page;

    }

}
