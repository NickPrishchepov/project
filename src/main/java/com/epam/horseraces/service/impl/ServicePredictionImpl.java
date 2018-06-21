package com.epam.horseraces.service.impl;

import com.epam.horseraces.dao.entity.Prediction;
import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.entity.enums.ResultType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.PredictionDaoImpl;
import com.epam.horseraces.dao.impl.QuotationDaoImpl;

import java.util.List;

/**
 * The {@code ServicePredictionImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ServicePredictionImpl {

    /**
     * @param userId      The user id
     * @param quotationId The quotation id
     * @param money       The money
     * @throws DaoException If the query was competed with errors
     */
    public void addPrediction(int userId, int quotationId, Double money) throws DaoException {

        Prediction prediction = new Prediction();
        prediction.setUserId(userId);
        prediction.setQuotationId(quotationId);
        prediction.setMoney(money);

        PredictionDaoImpl dao = new PredictionDaoImpl();
        dao.addRecord(prediction);

    }

    /**
     * @param id The prediction id
     * @throws DaoException If the query was competed with erors
     */
    public void deletePrediction(int id) throws DaoException {

        Prediction prediction = new Prediction();
        prediction.setId(id);

        PredictionDaoImpl dao = new PredictionDaoImpl();
        dao.deleteRecord(prediction);

    }

    /**
     * @param userId      The user id
     * @param quotationId The quotation id
     * @param money       The money
     * @throws DaoException If the query was competed with errors
     */
    public void updatePrediction(int predictionId, int userId, int quotationId, double money) throws DaoException {

        Prediction prediction = new Prediction();
        prediction.setId(predictionId);
        prediction.setUserId(userId);
        prediction.setQuotationId(quotationId);
        prediction.setMoney(money);

        PredictionDaoImpl dao = new PredictionDaoImpl();
        dao.updateRecord(prediction);

    }

    /**
     * @throws DaoException If the query was competed with errors
     */
    public void changeStatus() throws DaoException {

        QuotationDaoImpl quotationDao = new QuotationDaoImpl();
        List<Quotation> quotations = quotationDao.getAllTBDRecords();

        PredictionDaoImpl predictionDao = new PredictionDaoImpl();
        List<Prediction> predictions = predictionDao.getAllRecords();

        for (Prediction prediction : predictions) {
            checkPrediction(quotations, prediction, predictionDao);
        }
    }

    /**
     * @param quotations    The list of {@code Quotation} objects
     * @param prediction    The list of {@code Quotation} objects
     * @param predictionDao The dao that provides and opportunity to
     *                      change prediction status
     * @throws DaoException If the query was competed with errors
     */
    private void checkPrediction(List<Quotation> quotations, Prediction prediction, PredictionDaoImpl predictionDao) throws DaoException {
        for (Quotation quotation : quotations) {
            Integer predictionQuotationId = prediction.getQuotationId();
            Integer quotationId = quotation.getId();
            if (predictionQuotationId.equals(quotationId)) {
                changePrediction(quotation, prediction);
                predictionDao.changeStatus(prediction);
            }
        }
    }

    /**
     * @param quotation  The {@code Quotation} object
     * @param prediction The {@code Prediction} object
     */
    private void changePrediction(Quotation quotation, Prediction prediction) {
        if (quotation.getResultType() == ResultType.WIN) {
            prediction.setResultType(ResultType.WIN);
        } else if (quotation.getResultType() == ResultType.LOSE) {
            prediction.setResultType(ResultType.LOSE);
        }
    }


}
