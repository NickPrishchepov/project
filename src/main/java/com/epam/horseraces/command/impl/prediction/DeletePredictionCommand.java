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
 * The {@code DeletePredictionCommand} class is responsive for
 * making delete operations on {@code Prediction} objects. It receives an attribute that
 * describes the unique identifier of the {@code Prediction} object. It calls
 * method that receives existing identifier and then delete the corresponding record
 * from database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class DeletePredictionCommand implements ActionCommand {

    private static final String ATTRIBUTE = "attribute";
    private static final String PREDICTION_PAGE = "path.page.predictionpage";
    private static final String LOGGING_MESSAGE = "The deleting of prediction wasn't finished successfully: ";

    private static final String IS_FAILED_DELETE_PREDICTION = "isFailedDeletePrediction";
    private static final boolean FAILED = true;

    private static Logger logger = LogManager.getLogger(DeletePredictionCommand.class);


    /**
     * The method execute delete operation calling the service methods
     * and return the main prediction page
     *
     * @param request Contains attributes that are necessary for
     *                making delete operations
     * @return The main prediction page which is providing add,
     * delete and edit operations on {@code Prediction} objects
     */
    @Override
    public String execute(HttpServletRequest request) {

        String page;

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(ATTRIBUTE);

        try {

            ServicePredictionImpl servicePrediction = new ServicePredictionImpl();
            servicePrediction.deletePrediction(id);

            ShowPredictionsCommand showPredictionsCommand = new ShowPredictionsCommand();
            page = showPredictionsCommand.execute(request);

        } catch (DaoException e) {

            request.setAttribute(IS_FAILED_DELETE_PREDICTION, FAILED);
            page = ConfigurationManager.getProperty(PREDICTION_PAGE);
            logger.error(LOGGING_MESSAGE + e);

        }

        return page;
    }
}
