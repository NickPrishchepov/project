package com.epam.horseraces.command.impl.horse;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServiceHorseImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code DeleteHorseCommand} class is responsive for
 * making delete operations on {@code Horse} objects. It receives an attribute that
 * describes the unique identifier of the {@code Horse} object. It calls
 * method that receives existing identifier and then delete the corresponding record
 * from database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class DeleteHorseCommand implements ActionCommand {

    private static final String HORSE_ID = "horse_id";
    private static final String LOGGING_MESSAGE = "The deleting of horse hasn't successfully finished: ";
    private static final String COMMAND = "command";
    private static final String SHOW_HORSES = "SHOW_HORSES";
    private static final String HORSE_PAGE = "path.page.horsepage";
    private static final String IS_FAILED_DELETE_HORSE = "isFailedDeleteHorse";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(DeleteHorseCommand.class);

    /**
     * The method execute delete operation calling the service methods
     * and return the main horse page
     *
     * @param request Contains attributes that are necessary for
     *                making delete operations
     * @return The main horse page which is providing add,
     * delete and edit operations on {@code Horse} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(HORSE_ID);
        session.setAttribute(COMMAND, SHOW_HORSES);

        try {

            ServiceHorseImpl serviceHorse = new ServiceHorseImpl();
            serviceHorse.deleteHorse(id);

            ShowHorsesCommand showHorsesCommand = new ShowHorsesCommand();
            page = showHorsesCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_DELETE_HORSE, FAILED);
            page = ConfigurationManager.getProperty(HORSE_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }
}
