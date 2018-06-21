package com.epam.horseraces.command.impl.results;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.command.impl.race.ShowCommand;
import com.epam.horseraces.dao.entity.Result;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.ResultDaoImpl;
import com.epam.horseraces.service.impl.ServicePredictionImpl;
import com.epam.horseraces.service.impl.ServiceQuotationImpl;
import com.epam.horseraces.service.impl.ServiceRaceImpl;
import com.epam.horseraces.service.impl.ServiceResultImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The {@code MakeResultsCommand} class is responsive for generating results
 * of all {@code Race} objects that exists at the moment
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class MakeResultsCommand implements ActionCommand {

    private static final String PATH_PAGE_USERMAIN = "path.page.userpage";
    private static final String LOGGING_MESSAGE = "Showing users information's impossible: ";
    private static final String RESULT_PAGE = "path.page.resultpage";
    private static Logger logger = LogManager.getLogger(ShowCommand.class);
    private static final String LIST = "list";

    private static final String IS_FAILED_GENERATE_RESULTS = "isFailedGenerateResults";
    private static final boolean FAILED = true;

    /**
     * @param request It's used for saving information about
     *                {@code Result} object to realize some operation
     *                on the object
     * @return The web page that shows information about
     * generated {@code Result} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        ServiceResultImpl serviceResult = new ServiceResultImpl();
        ServiceQuotationImpl serviceQuotation = new ServiceQuotationImpl();
        ServiceRaceImpl serviceRace = new ServiceRaceImpl();
        ServicePredictionImpl servicePrediction = new ServicePredictionImpl();

        try {

            serviceResult.generateResults();
            setResultRequest(request);
            serviceRace.changeRaceStatus();
            serviceQuotation.changeRaceStatus();
            servicePrediction.changeStatus();

            page = ConfigurationManager.getProperty(RESULT_PAGE);

        } catch (DaoException e) {
            HttpSession session = request.getSession();
            session.setAttribute(IS_FAILED_GENERATE_RESULTS, FAILED);
            page = ConfigurationManager.getProperty(PATH_PAGE_USERMAIN);
            logger.error(LOGGING_MESSAGE + e);
        }

        return page;
    }

    /**
     * @param request It's applied for saving existing list of
     *                {@code Result} objects to display them on the appropriate
     *                web page
     * @throws DaoException If the list of {@code Result} objects is empty
     */
    private void setResultRequest(HttpServletRequest request) throws DaoException {
        ResultDaoImpl dao = new ResultDaoImpl();
        List<Result> results = dao.getAllRecords();
        HttpSession session = request.getSession();
        session.setAttribute(LIST, results);
    }
}
