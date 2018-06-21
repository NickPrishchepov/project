package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.PredictionDao;
import com.epam.horseraces.dao.entity.Prediction;
import com.epam.horseraces.dao.entity.enums.ResultType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PredictionDaoImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class PredictionDaoImpl extends AbstractGeneralDaoImpl<Prediction> implements PredictionDao {

    private static final String INSERT_QUERY = "insert into predictions(user_id,quotation_id,amount_of_money) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select prediction_id, user_id,quotation_id,amount_of_money, is_victorious from predictions where is_victorious = 0";
    private static final String DELETE_QUERY = "delete from predictions where prediction_id = ?";
    private static final String UPDATE_QUERY = "update predictions set quotation_id = ?, amount_of_money = ? where prediction_id = ?";
    private static final String SELECT_BY_ID_QUERY = "select prediction_id,user_id,quotation_id,amount_of_money,is_victorious from predictions where user_id = ? and is_victorious = 0";
    private static final String CHANGE_STATUS_QUERY = "update predictions set is_victorious = ? where prediction_id = ?";
    private static final String HISTORY_QUERY = "select prediction_id,user_id,quotation_id,amount_of_money,is_victorious from predictions where user_id = ? and is_victorious != 0";


    /**
     * @param preparedStatement Statement which is used for making the
     *                          change query
     * @param object            The appropriate object which status should
     *                          be changed
     * @throws SQLException If the query was competed with errors
     */
    private void changeStatus(PreparedStatement preparedStatement, Prediction object) throws SQLException {


        ResultType resultType = object.getResultType();
        Integer resultTypeId = resultType.getId();
        Integer id = object.getId();
        preparedStatement.setInt(1, resultTypeId);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();

    }

    /**
     * @return The prediction histroy query
     */
    private String getPredictionHistoryQuery() {
        return HISTORY_QUERY;
    }

    /**
     * @return The insert query
     */
    @Override
    public String getInsertQuery() {
        return INSERT_QUERY;
    }

    /**
     * @return The update query
     */
    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    /**
     * @return The select query
     */
    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    /**
     * @return The delete query
     */
    @Override
    public String getDeleteQuery() {
        return DELETE_QUERY;
    }

    /**
     * @return The select by id query
     */
    private String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    /**
     * @return The change status query
     */
    private String getChangeStatusQuery() {
        return CHANGE_STATUS_QUERY;
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          insert query
     * @param object            The appropriate object that should
     *                          be added
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void insertRecord(PreparedStatement preparedStatement, Prediction object) throws SQLException {
        Integer user_id = object.getUserId();
        Integer quotation_id = object.getQuotationId();
        Double money = object.getMoney();
        preparedStatement.setInt(1, user_id);
        preparedStatement.setInt(2, quotation_id);
        preparedStatement.setDouble(3, money);
        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Prediction>} objects
     * @throws SQLException If the query was competed with errors
     */
    public List<Prediction> getRecordsId(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Prediction>} objects
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public List<Prediction> getRecords(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          delete query
     * @param object            The appropriate object that should
     *                          be deleted
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void deleteRecord(PreparedStatement preparedStatement, Prediction object) throws SQLException {
        Integer id = object.getId();
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          update query
     * @param object            The appropriate object that should
     *                          be modified
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void updateRecord(PreparedStatement preparedStatement, Prediction object) throws SQLException {

        Integer id = object.getId();
        Integer quotation_id = object.getQuotationId();
        Double money = object.getMoney();
        preparedStatement.setInt(1, quotation_id);
        preparedStatement.setDouble(2, money);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Prediction>} objects
     * @throws SQLException If the query was competed with errors
     */
    private List<Prediction> makeList(ResultSet resultSet) throws SQLException {
        List<Prediction> predictions = new ArrayList<Prediction>();
        while (resultSet.next()) {

            Integer id = resultSet.getInt(1);
            Integer user_id = resultSet.getInt(2);
            Integer quotation_id = resultSet.getInt(3);
            Double money = resultSet.getDouble(4);
            Integer resultTypeId = resultSet.getInt(5);
            ResultType resultType = ResultType.getById(resultTypeId);


            Prediction prediction = new Prediction();
            prediction.setId(id);
            prediction.setUserId(user_id);
            prediction.setQuotationId(quotation_id);
            prediction.setMoney(money);
            prediction.setResultType(resultType);
            predictions.add(prediction);
        }
        return predictions;
    }

    /**
     * @param id The user's identifier
     * @return The list of {@code <Prediction>} objects
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public List<Prediction> getHistoryRecords(Integer id) throws DaoException {

        ConnectionPool connectionPool = new ConnectionPool(false);
        Connection connection;

        List<Prediction> predictions;

        try {
            connection = connectionPool.retrieve();
            String query = getPredictionHistoryQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            predictions = getRecordsId(resultSet);
            if (predictions.size() == 0) {
                throw new DaoException("The list size is 0");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return predictions;
    }

    /**
     * @param object The appropriate object which status should
     *               be changed
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public void changeStatus(Prediction object) throws DaoException {
        ConnectionPool connectionPool = new ConnectionPool(false);

        String query = getChangeStatusQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            changeStatus(preparedStatement, object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * @param id The user's identifier
     * @return The list of {@code <Prediction>} objects
     * @throws DaoException If the query was competed with errors
     */
    public List<Prediction> getRecordsById(Integer id) throws DaoException {
        ConnectionPool connectionPool = new ConnectionPool(false);
        List<Prediction> types;
        String query = getSelectByIdQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            types = getRecordsId(resultSet);
            if (types.size() == 0) {
                throw new DaoException("The list size is 0");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return types;
    }
}
