package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.entity.Prediction;
import com.epam.horseraces.dao.impl.classes.TestPredictionDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PredictionDaoImplTest {

    private static ConnectionPool connectionPool = new ConnectionPool(true);

    private static final String INSERT_QUERY = "insert into predictions(user_id,quotation_id,amount_of_money) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select prediction_id, user_id,quotation_id,amount_of_money, is_victorious from predictions where is_victorious = 0";
    private static final String DELETE_QUERY = "delete from predictions where prediction_id = ?";
    private static final String UPDATE_QUERY = "update predictions set quotation_id = ?, amount_of_money = ? where prediction_id = ?";

    private static final String DELETE_INSERT_PREDICTION_QUERY = "delete from predictions where amount_of_money = 8000.0 and user_id = 20 and quotation_id = 20";
    private static final String INSERT_PREDICTION_FOR_DELETE_QUERY = "insert into predictions(prediction_id,user_id,quotation_id,amount_of_money) values(2,20,20,5000.0)";
    private static final String DELETE_UPDATE_PREDICTION_QUERY = "delete from predictions where prediction_id = 3 and amount_of_money = 20000.0";

    private static final String INSERT_PREDICTION_FOR_UPDATE_QUERY = "insert into predictions(prediction_id,user_id,quotation_id,amount_of_money) values(3,20,20,9000.0)";

    private static final String INSERT_PREDICTION_FOR_SELECT_QUERY = "insert into predictions(prediction_id,user_id,quotation_id,amount_of_money) values(4,20,20,12000.0)";
    private static final String DELETE_PREDICTION_FOR_SELECT_QUERY = "delete from predictions where prediction_id = 4 and amount_of_money = 12000.0";
    private static final String DELETE_EXISTING_PREDICTION_QUERY = "delete from predictions where prediction_id = 2 and user_id = 20 and quotation_id = 20 and amount_of_money = 8000.0";


    private String getInsertPredictionForSelectQuery(){
        return INSERT_PREDICTION_FOR_SELECT_QUERY;
    }

    private static String getDeletePredictionForSelectQuery() {
        return DELETE_PREDICTION_FOR_SELECT_QUERY;
    }



    private String getDeleteExistingPredictionQuery() {
        return DELETE_EXISTING_PREDICTION_QUERY;
    }

    @Test
    public void insertRecord() throws SQLException {

        String insertQuery = getInsertQuery();
        PreparedStatement preparedStatement = getPreparedStatement(insertQuery);

        Prediction prediction = getInsertPrediction();

        TestPredictionDaoImpl testDao = getTestDao();
        testDao.insertRecord(preparedStatement,prediction);

        String deleteInsertPredictionQuery = getDeleteInsertPredictionQuery();

        Integer actualResultSetSize = getActualNumRowsAffected(deleteInsertPredictionQuery);
        Integer expectedResultSetSize = 1;

        assertEquals(actualResultSetSize,expectedResultSetSize);

    }

    private String getDeleteInsertPredictionQuery(){
        return DELETE_INSERT_PREDICTION_QUERY;
    }

    private Prediction getInsertPrediction(){

        Prediction prediction = new Prediction();

        prediction.setId(1);
        prediction.setQuotationId(20);
        prediction.setUserId(20);
        prediction.setMoney(8000.0);

        return prediction;

    }

    @Test
    public void delRecord() throws SQLException {

        String insertPredictionForDeleteQuery = getInsertPredictionForDeleteQuery();

        executeQuery(insertPredictionForDeleteQuery);

        String deleteQuery = getDeleteQuery();
        PreparedStatement preparedStatement = getPreparedStatement(deleteQuery);
        Prediction prediction = getDeletePrediction();



        TestPredictionDaoImpl predictionDao = getTestDao();
        predictionDao.delRecord(preparedStatement,prediction);

        String deleteExistingPredictionQuery = getDeleteExistingPredictionQuery();

        int value = getActualNumRowsAffected(deleteExistingPredictionQuery);
        assertEquals(value,0);

    }

    private String getInsertPredictionForDeleteQuery(){
        return INSERT_PREDICTION_FOR_DELETE_QUERY;
    }

    private Prediction getDeletePrediction(){

        Prediction prediction = new Prediction();

        prediction.setId(2);
        prediction.setUserId(20);
        prediction.setQuotationId(20);
        prediction.setMoney(5000.0);

        return prediction;

    }

    private void executeQuery(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

    }

    @Test
    public void updRecord() throws SQLException {

        String insertPredictionForUpdateQuery = getInsertPredictionForUpdateQuery();

        executeQuery(insertPredictionForUpdateQuery);

        String updateQuery = getUpdateQuery();
        PreparedStatement preparedStatement = getPreparedStatement(updateQuery);
        Prediction prediction = getUpdatePrediction();
        TestPredictionDaoImpl predictionDao = getTestDao();
        predictionDao.updRecord(preparedStatement,prediction);

        String deleteUpdateQuotationQuery = getDeleteUpdatePredictionQuery();


        int value = getActualNumRowsAffected(deleteUpdateQuotationQuery);
        assertEquals(value,1);

    }
    private static String getInsertPredictionForUpdateQuery() {
        return INSERT_PREDICTION_FOR_UPDATE_QUERY;
    }

    private String getDeleteUpdatePredictionQuery(){
        return DELETE_UPDATE_PREDICTION_QUERY;
    }


    @Test
    public void getRecords() throws SQLException {

        String insertPredictionForSelectQuery = getInsertPredictionForSelectQuery();
        String deletePredictionForSelectQuery = getDeletePredictionForSelectQuery();

        executeQuery(insertPredictionForSelectQuery);
        ResultSet resultSet = getResultSet();
        executeQuery(deletePredictionForSelectQuery);

        TestPredictionDaoImpl testDao = getTestDao();
        List<Prediction> predictions = testDao.getRecords(resultSet);

        Prediction prediction = predictions.get(0);

        int id = prediction.getId();
        int user_id = prediction.getUserId();
        int quotation_id = prediction.getQuotationId();
        double money = prediction.getMoney();


        assertEquals(id,4);
        assertEquals(user_id,20);
        assertEquals(user_id,20);
        assertEquals(quotation_id,20);
        assertEquals(money,12000.0,0.1);
    }

    private Prediction getUpdatePrediction(){

        Prediction prediction = new Prediction();

        prediction.setId(3);
        prediction.setUserId(20);
        prediction.setQuotationId(20);
        prediction.setMoney(20000.0);

        return prediction;
    }



    private Integer getActualNumRowsAffected(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);

    }

    private ResultSet getResultSet() throws SQLException {
        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();
        String kek = getSelectAllQuery();
        return statement.executeQuery(kek);
    }

    private PreparedStatement getPreparedStatement(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        return connection.prepareStatement(query);
    }


    private TestPredictionDaoImpl getTestDao(){
        return new TestPredictionDaoImpl();
    }

    private String getInsertQuery(){
        return INSERT_QUERY;
    }

    private String getSelectAllQuery(){
        return SELECT_ALL_QUERY;
    }

    private String getDeleteQuery(){
        return DELETE_QUERY;
    }

    private String getUpdateQuery(){
        return UPDATE_QUERY;
    }

}