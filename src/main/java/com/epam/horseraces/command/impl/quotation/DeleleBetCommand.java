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
 * The {@code DeleleBetCommand} class is responsive for
 * making delete operations on {@code Quotation} objects. It receives an attribute that
 * describes the unique identifier of the {@code Quotation} object. It calls
 * method that receives existing identifier and then delete the corresponding record
 * from database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class DeleleBetCommand implements ActionCommand {

    private static final String BET_ID = "attribute";
    private static final String BOOKMAKER_PAGE = "path.page.bookmakerpage";
    private static final String COMMAND = "command";
    private static final String SHOW_QUOTATIONS = "SHOW_QUOTATIONS";
    private static final String IS_FAILED_DELETE_BET = "isFailedDeleteBet";
    private static final boolean FAILED = true;
    private static final String LOGGING_MESSAGE = "The deleting of bet wasn't finished successfully: ";

    private static Logger logger = LogManager.getLogger(DeleleBetCommand.class);

    /**
     * The method execute delete operation calling the service methods
     * and return the main bookmaker page
     *
     * @param request Contains attributes that are necessary for
     *                making delete operations
     * @return The main quotation page which is providing add,
     * delete and edit operations on {@code Quotation} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        session.setAttribute(COMMAND, SHOW_QUOTATIONS);
        Integer id = (Integer) session.getAttribute(BET_ID);


        try {

            ServiceQuotationImpl serviceQuotation = new ServiceQuotationImpl();
            serviceQuotation.deleteQuotation(id);

            ShowQuotationsCommand showQuotationsCommand = new ShowQuotationsCommand();
            page = showQuotationsCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_DELETE_BET, FAILED);
            page = ConfigurationManager.getProperty(BOOKMAKER_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;

    }
}
