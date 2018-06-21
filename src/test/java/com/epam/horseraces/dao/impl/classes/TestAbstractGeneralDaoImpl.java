package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.GeneralDao;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

public abstract class TestAbstractGeneralDaoImpl<Type extends Serializable> implements Dao<Type> {

    private static ConnectionPool connectionPool = new ConnectionPool(true);

    //private static final Logger LOGGER = LogManager.getLogger(AbstractGeneralDaoImpl.class);

    public abstract String getInsertQuery();
    public abstract String getUpdateQuery();
    public abstract String getDeleteQuery();
    public abstract String getSelectAllQuery();
    public abstract String getSelectByIdQuery();
    public abstract String getChangeStatusQuery();
    public abstract void insertRecord(PreparedStatement preparedStatement, Type object) throws SQLException;
    public abstract List<Type> getRecords(ResultSet resultSet) throws SQLException;
    public abstract void delRecord(PreparedStatement preparedStatement,Type object) throws SQLException;
    public abstract void updRecord(PreparedStatement preparedStatement,Type object) throws SQLException;
    public abstract List<Type> getRecordsId(ResultSet resultSet) throws SQLException;
    public abstract void changingStatus(PreparedStatement preparedStatement, Type object) throws SQLException;
    public abstract String getTBDQuery();

    @Override
    public void changeStatus(Type object) throws DaoException {
        String query = getChangeStatusQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            changingStatus(preparedStatement,object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void addRecord(Type object) throws DaoException {
        String query = getInsertQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            insertRecord(preparedStatement,object);
        } catch (SQLException e) {
            throw new DaoException("kek");
        }
    }

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
            if(types.size() == 0){
                throw new DaoException("The list size is 0");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return types;
    }

    @Override
    public void deleteRecord(Type object) throws DaoException {
        String query = getDeleteQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            delRecord(preparedStatement,object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateRecord(Type object) throws DaoException {
        String query = getUpdateQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            updRecord(preparedStatement,object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Type> getRecordsById(Integer id) throws DaoException {
        List<Type> types;
        String query = getSelectByIdQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            types = getRecordsId(resultSet);
            if(types.size() == 0){
                throw new DaoException("The list size is 0");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return types;
    }

    @Override
    public List<Type> getAllTBDRecords() throws DaoException {
        List<Type> types;
        String query = getTBDQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            types = getRecords(resultSet);
            if(types.size() == 0){
                throw new DaoException("The list size is 0");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return types;
    }
}
