package com.epam.horseraces.dao;

import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.exception.DaoException;

import java.util.List;

/**
 * The {@code QuotationDao} interface provides some methods of
 * the work with the database source. All the classes using this interface
 * should implement the methods
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public interface QuotationDao extends GeneralDao<Quotation> {

    /**
     * @param id The user's identifier
     * @return The list of {@code <Quotation>} objects
     * @throws DaoException If the query was competed with errors
     */
    List<Quotation> getHistoryRecords(Integer id) throws DaoException;

    /**
     * @param id The user's identifier
     * @return The list of {@code <Quotation>} objects
     * @throws DaoException If the query was competed with errors
     */
    List<Quotation> getRecordsById(Integer id) throws DaoException;

    /**
     * @param object The appropriate object which status should
     *               be changed
     * @throws DaoException If the query was competed with errors
     */
    void changeStatus(Quotation object) throws DaoException;

    /**
     * @return The list of {@code <Quotation>} objects
     * @throws DaoException If the query was competed with errors
     */
    List<Quotation> getAllTBDRecords() throws DaoException;

}
