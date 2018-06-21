package com.epam.horseraces.command.impl.race;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServiceRaceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code DeleteRaceCommand} class is responsive for
 * making delete operations on {@code Race} objects. It receives an attribute that
 * describes the unique identifier of the {@code Race} object. It calls
 * method that receives existing identifier and then delete the corresponding record
 * from database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class DeleteRaceCommand implements ActionCommand {

    private static final String RACE_ID = "race_id";
    private static final String RACE_PAGE = "path.page.racepage";
    private static final String COMMAND = "command";
    private static final String SHOW_RACES = "SHOW_RACES";

    private static final String IS_FAILED_DELETE_RACE = "isFailedDeleteRace";
    private static final boolean FAILED = true;

    private static final String LOGGING_MESSAGE = "The deleting of race hasn't successfully finished: ";

    private static Logger logger = LogManager.getLogger(DeleteRaceCommand.class);

    /**
     * The method execute delete operation calling the service methods
     * and return the main race page
     *
     * @param request Contains attributes that are necessary for
     *                making delete operations
     * @return The main race page which is providing add,
     * delete and edit operations on {@code Race} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        session.setAttribute(COMMAND, SHOW_RACES);

        Integer id = (Integer) session.getAttribute(RACE_ID);

        try {

            ServiceRaceImpl serviceRace = new ServiceRaceImpl();
            serviceRace.deleteRace(id);

            ShowCommand showCommand = new ShowCommand();
            page = showCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_DELETE_RACE, FAILED);
            page = ConfigurationManager.getProperty(RACE_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }

}

