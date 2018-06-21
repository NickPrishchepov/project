package com.epam.horseraces.dao;

import com.epam.horseraces.dao.exception.DaoException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * The {@code GeneralDao} interface provides some methods of
 * the work with the database source. All the classes using this interface
 * should implement the methods
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public interface GeneralDao<Type extends Serializable> {

    /**
     * @param object The object that should be inserted
     * @throws SQLException If the query was competed with errors
     * @throws DaoException If the query was competed with errors
     */
    void addRecord(Type object) throws SQLException, DaoException;

    /**
     * @param object The object that should be deleted
     * @throws SQLException If the query was competed with errors
     * @throws DaoException If the query was competed with errors
     */
    void deleteRecord(Type object) throws SQLException, DaoException;

    /**
     * @param object The object that should be modified
     * @throws SQLException If the query was competed with errors
     * @throws DaoException If the query was competed with errors
     */
    void updateRecord(Type object) throws SQLException, DaoException;

    /**
     * @return The list of {@code <Type>} records
     * @throws SQLException If the query was competed with errors
     * @throws DaoException If the query was competed with errors
     */
    List<Type> getAllRecords() throws SQLException, DaoException;

}
