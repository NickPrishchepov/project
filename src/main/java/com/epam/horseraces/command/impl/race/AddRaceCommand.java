package com.epam.horseraces.command.impl.race;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServiceRaceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * The {@code AddRaceCommand} class is responsive for
 * making add operations on {@code Race} objects. It receives an attributes
 * describing the {@code Race} object. Then {@code AddRaceCommand} class calls
 * methods of making add existing information to database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class AddRaceCommand implements ActionCommand {

    private static final String DATE = "date";
    private static final String LOCATION = "location";
    private static final String TYPE = "type";

    private static final String IS_FAILED_ADD_RACE = "isFailedAddRace";
    private static final boolean FAILED = true;

    private static final String RACE_PAGE = "path.page.racepage";
    private static final String COMMAND = "command";
    private static final String SHOW_RACES = "SHOW_RACES";
    private static final String LOGGING_MESSAGE = "The addition of race wasn't finished successfully: ";
    private static final String INCORRECT_DATE = "The date is incorrect.";

    private static Logger logger = LogManager.getLogger(AddRaceCommand.class);

    /**
     * The method calls the service methods that provides add
     * operations of {@code Race} objects and sends to the
     * main race page
     *
     * @param request Contains attributes that are needed for
     *                realizing add operations
     * @return The main race page which is providing add,
     * delete and edit operations on {@code Race} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        session.setAttribute(COMMAND, SHOW_RACES);

        String location = request.getParameter(LOCATION);
        String type = request.getParameter(TYPE);
        String stringDate = request.getParameter(DATE);

        try {

            ServiceRaceImpl serviceRace = new ServiceRaceImpl();

            Date date = getCorrectDate(serviceRace, stringDate);
            serviceRace.addRace(date, location, type);

            ShowCommand showCommand = new ShowCommand();
            page = showCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_ADD_RACE, FAILED);
            page = ConfigurationManager.getProperty(RACE_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }

    /**
     * The method sets and returns the right date format from
     * the original date version
     *
     * @param serviceRace The service that provides an opportunity to
     *                    get the needed date in correct format
     * @param stringDate  The date in original form
     * @return The date in modified  form
     * @throws DaoException If the existing date is incorrect
     */
    private Date getCorrectDate(ServiceRaceImpl serviceRace, String stringDate) throws DaoException {

        Date correctDate;

        Date sqlDate = serviceRace.getSqlDateFormat(stringDate);

        Long utilFormatDate = new Date().getTime();
        java.sql.Date currentDate = new java.sql.Date(utilFormatDate);

        if (sqlDate.getTime() < currentDate.getTime()) {
            throw new DaoException(INCORRECT_DATE);
        } else {
            correctDate = sqlDate;
        }

        return correctDate;

    }
}
