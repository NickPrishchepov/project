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
 * The {@code AddHorseCommand} class is responsive for
 * making add operations on {@code Horse} objects. It receives an attributes
 * describing the {@code Horse} object. Then {@code AddHorseCommand} class calls
 * methods of making add existing information to database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class AddHorseCommand implements ActionCommand {

    private static final String NICKNAME = "nickname";
    private static final String BREED = "breed";
    private static final String AGE = "age";
    private static final String JOCKEY = "jockey";
    private static final String RATING = "rating";
    private static final String LOGGING_MESSAGE = "The addition of horse hasn't successfully finished: ";
    private static final String IS_FAILED_ADD_HORSE = "isFailedAddHorse";
    private static final String COMMAND = "command";
    private static final String SHOW_HORSES = "SHOW_HORSES";
    private static final String HORSE_PAGE = "path.page.horsepage";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(AddHorseCommand.class);

    /**
     * The method calls the service methods that provides add
     * operations of {@code Horse} objects and sends to the
     * main horse page
     *
     * @param request Contains attributes that are needed for
     *                realizing add operations
     * @return The main horse page which is providing add,
     * delete and edit operations on {@code Horse} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        session.setAttribute(COMMAND, SHOW_HORSES);

        String nickname = request.getParameter(NICKNAME);
        String breed = request.getParameter(BREED);
        String stringHorseAge = request.getParameter(AGE);
        Integer horseAge = Integer.parseInt(stringHorseAge);
        String jockey = request.getParameter(JOCKEY);
        String stringRating = request.getParameter(RATING);
        Double horseRating = Double.parseDouble(stringRating);

        try {

            ServiceHorseImpl serviceHorse = new ServiceHorseImpl();
            serviceHorse.addHorse(nickname, breed, horseAge, jockey, horseRating);

            ShowHorsesCommand showHorsesCommand = new ShowHorsesCommand();
            page = showHorsesCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_ADD_HORSE, FAILED);
            page = ConfigurationManager.getProperty(HORSE_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }
}
