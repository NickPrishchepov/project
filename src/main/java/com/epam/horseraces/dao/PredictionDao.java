package com.epam.horseraces.dao;

import com.epam.horseraces.dao.entity.Prediction;
import com.epam.horseraces.dao.exception.DaoException;

import java.util.List;

/**
 * The {@code PredictionDao} interface provides some methods of
 * the work with the database source. All the classes using this interface
 * should implement the methods
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public interface PredictionDao extends GeneralDao<Prediction> {

    /**
     * @param id The user's identifier
     * @return The list of {@code <Prediction>} objects
     * @throws DaoException If the query was competed with errors
     */
    List<Prediction> getHistoryRecords(Integer id) throws DaoException;

    /**
     * @param id The user's identifier
     * @return The list of {@code <Prediction>} objects
     * @throws DaoException If the query was competed with errors
     */
    List<Prediction> getRecordsById(Integer id) throws DaoException;

    /**
     * @param object The appropriate object which status should
     *               be changed
     * @throws DaoException If the query was competed with errors
     */
    void changeStatus(Prediction object) throws DaoException;

}
