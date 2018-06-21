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
 * The {@code EditPredictionCommand} class is responsive for
 * making edit operations on {@code Prediction} objects. It receives an attribute that
 * describes the unique identifier of the {@code Prediction} object. It calls
 * method that receives the identifier and then edit the corresponding record
 * in existing database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class EditPredictionCommand implements ActionCommand {

    private static final String ID = "id";
    private static final String ATTR = "attr";
    private static final String PREDICTION_ID = "prediction_id";
    private static final String QUOTATION_ID = "quotation_id";
    private static final String PATH_PAGE_PREDICTIONPAGE = "path.page.predictionpage";
    private static final String LOGGING_MESSAGE = "The edition of prediction wasn't finished successfully: ";

    private static final String IS_FAILED_EDIT_PREDICTION = "isFailedEditPrediction";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(EditPredictionCommand.class);

    /**
     * The method execute edit operation calling the service methods
     * and return the main prediction page
     *
     * @param request Contains attributes that are necessary for
     *                making edit operations
     * @return The main prediction page which is providing add,
     * delete and edit operations on {@code Prediction} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        Integer prediction_id = (Integer) session.getAttribute(PREDICTION_ID);
        Integer user_id = (Integer) session.getAttribute(ID);
        Integer quotation_id = (Integer) session.getAttribute(QUOTATION_ID);

        String stringMoney = request.getParameter(ATTR);
        Double money = Double.parseDouble(stringMoney);


        try {

            ServicePredictionImpl servicePrediction = new ServicePredictionImpl();
            servicePrediction.updatePrediction(prediction_id, user_id, quotation_id, money);

            ShowPredictionsCommand showPredictionsCommand = new ShowPredictionsCommand();
            page = showPredictionsCommand.execute(request);

        } catch (DaoException e) {

            request.setAttribute(IS_FAILED_EDIT_PREDICTION, FAILED);
            page = ConfigurationManager.getProperty(PATH_PAGE_PREDICTIONPAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;
    }
}
