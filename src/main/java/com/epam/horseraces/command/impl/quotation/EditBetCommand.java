package com.epam.horseraces.command.impl.quotation;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServiceQuotationImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code EditBetCommand} class is responsive for
 * making edit operations on {@code Quotation} objects. It receives an attribute that
 * describes the unique identifier of the {@code Quotation} object. It calls
 * method that receives the identifier and then edit the corresponding record
 * in existing database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class EditBetCommand implements ActionCommand {

    private static final String QUOTATION_ID = "quotation_id";
    private static final String USER_ID = "id";
    private static final String RACE_ID = "race_id";
    private static final String HORSE_ID = "horse_id";
    private static final String DESCRIPTION = "description";
    private static final String COEFFICIENT = "coefficient";
    private static final String BOOKMAKER_PAGE = "path.page.bookmakerpage";
    private static final String PLACE = "description_show";
    private static final String EMPTY_STRING = "";
    private static final String LOGGING_MESSAGE = "The edition of bet wasn't finished successfully: ";
    private static final String COMMAND = "command";
    private static final String SHOW_QUOTATIONS = "SHOW_QUOTATIONS";
    private static final String IS_FAILED_EDIT_BET = "isFailedEditBet";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(EditBetCommand.class);

    /**
     * The method execute edit operation calling the service methods
     * and return the main bookmaker page
     *
     * @param request Contains attributes that are necessary for
     *                making edit operations
     * @return The main quotation page which is providing add,
     * delete and edit operations on {@code Quotation} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        session.setAttribute(COMMAND, SHOW_QUOTATIONS);
        Integer quotationId = (Integer) session.getAttribute(QUOTATION_ID);
        Integer userId = (Integer) session.getAttribute(USER_ID);
        Integer raceId = (Integer) session.getAttribute(RACE_ID);
        Integer horseId = (Integer) session.getAttribute(HORSE_ID);

        String description = request.getParameter(DESCRIPTION);
        String place = request.getParameter(PLACE);
        String stringCoefficient = request.getParameter(COEFFICIENT);
        Double coefficient = Double.parseDouble(stringCoefficient);
        String quotateDescription = getRightDescription(place, description);

        try {

            ServiceQuotationImpl serviceQuotation = new ServiceQuotationImpl();
            serviceQuotation.updateQuotation(quotationId, userId, raceId, horseId, quotateDescription, coefficient);

            ShowQuotationsCommand showQuotationsCommand = new ShowQuotationsCommand();
            page = showQuotationsCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_EDIT_BET, FAILED);
            page = ConfigurationManager.getProperty(BOOKMAKER_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;
    }

    /**
     * The method sets and returns the right bet description
     * from the two existing options
     *
     * @param place       The place of horse which is presented in
     *                    digital format
     * @param description The place of horse which is presented in
     *                    text format
     * @return The appropriate description
     */
    private String getRightDescription(String place, String description) {

        String quotateDescription;

        if (place.equalsIgnoreCase(EMPTY_STRING)) {
            quotateDescription = description;
        } else {
            quotateDescription = place;
        }

        return quotateDescription;

    }

}
