package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.PredictionDao;
import com.epam.horseraces.dao.entity.Prediction;
import com.epam.horseraces.dao.entity.enums.ResultType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.AbstractGeneralDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPredictionDaoImpl extends TestAbstractGeneralDaoImpl<Prediction> implements PredictionDao {

    private static final String INSERT_QUERY = "insert into predictions(user_id,quotation_id,amount_of_money) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select prediction_id, user_id,quotation_id,amount_of_money, is_victorious from predictions where is_victorious = 0";
    private static final String DELETE_QUERY = "delete from predictions where prediction_id = ?";
    private static final String UPDATE_QUERY = "update predictions set quotation_id = ?, amount_of_money = ? where prediction_id = ?";
    private static final String SELECT_BY_ID_QUERY = "select prediction_id,user_id,quotation_id,amount_of_money,is_victorious from predictions where user_id = ? and is_victorious = 0";
    private static final String CHANGE_STATUS_QUERY = "update predictions set is_victorious = ? where prediction_id = ?";
    private static final String HISTORY_QUERY = "select prediction_id,user_id,quotation_id,amount_of_money,is_victorious from predictions where user_id = ? and is_victorious != 0";

    @Override
    public String getTBDQuery() {
        return null;
    }

    @Override
    public void changingStatus(PreparedStatement preparedStatement, Prediction object) throws SQLException {


        ResultType resultType = object.getResultType();
        Integer resultTypeId = resultType.getId();
        Integer id = object.getId();
        preparedStatement.setInt(1, resultTypeId);
        preparedStatement.setInt(2,id);

        preparedStatement.executeUpdate();

    }

    private String getPredictionHistoryQuery(){
        return HISTORY_QUERY;
    }

    @Override
    public String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    public  String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    public String getChangeStatusQuery() {
        return CHANGE_STATUS_QUERY;
    }

    @Override
    public void insertRecord(PreparedStatement preparedStatement, Prediction object) throws SQLException {
        Integer user_id = object.getUserId();
        Integer quotation_id = object.getQuotationId();
        Double money = object.getMoney();
        preparedStatement.setInt(1, user_id);
        preparedStatement.setInt(2,quotation_id);
        preparedStatement.setDouble(3,money);
        preparedStatement.executeUpdate();
    }

    public List<Prediction> getRecordsId(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    @Override
    public List<Prediction> getRecords(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    @Override
    public void delRecord(PreparedStatement preparedStatement, Prediction object) throws SQLException {
        Integer id = object.getId();
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updRecord(PreparedStatement preparedStatement, Prediction object) throws SQLException {

        Integer id = object.getId();
        Integer quotation_id = object.getQuotationId();
        Double money = object.getMoney();
        preparedStatement.setInt(1, quotation_id);
        preparedStatement.setDouble(2,money);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    private List<Prediction> makeList(ResultSet resultSet) throws SQLException {
        List<Prediction> predictions = new ArrayList<Prediction>();
        while (resultSet.next()){

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
}
