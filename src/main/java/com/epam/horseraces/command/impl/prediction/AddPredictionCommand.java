package com.epam.horseraces.command.impl.prediction;

import com.epam.horseraces.command.ActionCommand;
import com.epam.horseraces.command.impl.manager.ConfigurationManager;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.service.impl.ServicePredictionImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code AddPredictionCommand} class is responsive for
 * making add operations on {@code Prediction} objects. It receives an attributes
 * describing the {@code Prediction} object. Then {@code AddPredictionCommand} class calls
 * methods of making add existing information to database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class AddPredictionCommand implements ActionCommand {

    private static final String ID = "id";
    private static final String ATTR = "attr";
    private static final String QUOTATION_ID = "quotation_id";
    private static final String PREDICTION_PAGE = "path.page.predictionpage";
    private static final String LOGGING_MESSAGE = "The addition of prediction wasn't finished successfully: ";
    private static final String IS_FAILED_ADD_PREDICTION = "isFailedAddPrediction";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(AddPredictionCommand.class);

    /**
     * The method calls the service methods that provides add
     * operations of {@code Prediction} objects and sends to the
     * main prediction page
     *
     * @param request Contains attributes that are needed for
     *                realizing add operations
     * @return The main prediction page which is providing add,
     * delete and edit operations on {@code Prediction} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        Integer user_id = (Integer) session.getAttribute(ID);
        Integer quotation_id = (Integer) session.getAttribute(QUOTATION_ID);

        String stringMoney = request.getParameter(ATTR);
        Double money = Double.parseDouble(stringMoney);

        try {

            ServicePredictionImpl servicePrediction = new ServicePredictionImpl();
            servicePrediction.addPrediction(user_id, quotation_id, money);

            ShowPredictionsCommand showPredictionsCommand = new ShowPredictionsCommand();
            page = showPredictionsCommand.execute(request);

        } catch (DaoException e) {

            session.setAttribute(IS_FAILED_ADD_PREDICTION, FAILED);
            page = ConfigurationManager.getProperty(PREDICTION_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;
    }
}
