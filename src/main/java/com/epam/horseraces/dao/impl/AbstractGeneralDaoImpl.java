package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.GeneralDao;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

/**
 * The {@code AbstractGeneralDaoImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @param <Type>
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public abstract class AbstractGeneralDaoImpl<Type extends Serializable> implements GeneralDao<Type> {

    private static final String EMPTY_LIST = "The selected list is empty";
    /**
     * The connection pool object
     */
    private static ConnectionPool connectionPool = new ConnectionPool(false);


    /**
     * The method returns the insert query
     *
     * @return Insert query
     */
    public abstract String getInsertQuery();

    /**
     * The method returns the update query
     *
     * @return The update query
     */
    public abstract String getUpdateQuery();

    /**
     * The method returns the delete query
     *
     * @return The delete query
     */
    public abstract String getDeleteQuery();

    /**
     * The method returns the select query
     *
     * @return The select query
     */
    public abstract String getSelectAllQuery();

    /**
     * The method makes an insert operation
     *
     * @param preparedStatement Statement which is used for making the
     *                          insert query
     * @param object            The appropriate object that should
     *                          be added
     * @throws SQLException If the query was competed with errors
     */
    public abstract void insertRecord(PreparedStatement preparedStatement, Type object) throws SQLException;

    /**
     * The method makes a delete operation
     *
     * @param preparedStatement Statement which is used for making the
     *                          delete query
     * @param object            The appropriate object that should
     *                          be deleted
     * @throws SQLException If the query was competed with errors
     */
    public abstract void deleteRecord(PreparedStatement preparedStatement, Type object) throws SQLException;

    /**
     * The method makes an update operation
     *
     * @param preparedStatement Statement which is used for making the
     *                          update query
     * @param object            The appropriate object that should
     *                          be modified
     * @throws SQLException If the query was competed with errors
     */
    public abstract void updateRecord(PreparedStatement preparedStatement, Type object) throws SQLException;

    /**
     * The method makes an select operation
     *
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Type>} objects
     * @throws SQLException If the query was competed with errors
     */
    public abstract List<Type> getRecords(ResultSet resultSet) throws SQLException;


    /**
     * The method makes an add operation
     *
     * @param object The appropriate object that should
     *               be added
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public void addRecord(Type object) throws DaoException {
        String query = getInsertQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            insertRecord(preparedStatement, object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * The method makes a select operation
     *
     * @return The list of {@code <Type>} objects
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public List<Type> getAllRecords() throws DaoException {
        List<Type> types;
        String query = getSelectAllQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            types = getRecords(resultSet);
            if (types.size() == 0) {
                throw new DaoException(EMPTY_LIST);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return types;
    }

    /**
     * The method makes an delete operation
     *
     * @param object The appropriate object that should
     *               be deleted
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public void deleteRecord(Type object) throws DaoException {
        String query = getDeleteQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            deleteRecord(preparedStatement, object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * The method makes an edit operation
     *
     * @param object The appropriate object that should
     *               be modified
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public void updateRecord(Type object) throws DaoException {
        String query = getUpdateQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            updateRecord(preparedStatement, object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
