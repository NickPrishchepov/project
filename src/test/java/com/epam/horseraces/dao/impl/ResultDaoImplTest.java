package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.entity.Result;
import com.epam.horseraces.dao.impl.classes.TestResultDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResultDaoImplTest {

    private static ConnectionPool connectionPool = new ConnectionPool(true);

    private static final String INSERT_QUERY = "insert into results(horse_id,race_id,place) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select result_id,horse_id,race_id,place from results";
    private static final String DELETE_QUERY = "delete from results where result_id = ?";
    private static final String UPDATE_QUERY = "update results set horse_id = ?, race_id = ?, place = ? where result_id = ?";

    private static final String DELETE_INSERT_RESULT_QUERY = "delete from results where horse_id = 20 and place = 1";
    private static final String INSERT_RESULT_FOR_DELETE_QUERY = "insert into results(result_id,horse_id,race_id,place) values(2,20,20,2)";
    private static final String DELETE_UPDATE_RESULT_QUERY = "delete from results where result_id = 3 and place = 5";

    private static final String INSERT_RESULT_FOR_UPDATE_QUERY = "insert into results(result_id,horse_id,race_id,place) values(3,20,20,3)";

    private static final String INSERT_RESULT_FOR_SELECT_QUERY = "insert into results(result_id,horse_id,race_id,place) values(4,20,20,1)";
    private static final String DELETE_RESULT_FOR_SELECT_QUERY = "delete from results where result_id = 4 and place = 1";
    private static final String DELETE_EXISTING_RESULT_QUERY = "delete from results where result_id = 2 and place = 2";


    private String getInsertResultForSelectQuery() {
        return INSERT_RESULT_FOR_SELECT_QUERY;
    }

    private static String getDeleteResultForSelectQuery() {
        return DELETE_RESULT_FOR_SELECT_QUERY;
    }


    private String getDeleteExistingResultQuery() {
        return DELETE_EXISTING_RESULT_QUERY;
    }

    @Test
    public void insertRecord() throws SQLException {

        String insertQuery = getInsertQuery();
        PreparedStatement preparedStatement = getPreparedStatement(insertQuery);

        Result result = getInsertResult();

        TestResultDaoImpl testDao = getTestDao();
        testDao.insertRecord(preparedStatement, result);

        String deleteInsertResultQuery = getDeleteInsertResultQuery();

        Integer actualResultSetSize = getActualNumRowsAffected(deleteInsertResultQuery);
        Integer expectedResultSetSize = 1;

        assertEquals(actualResultSetSize, expectedResultSetSize);

    }

    private String getDeleteInsertResultQuery() {
        return DELETE_INSERT_RESULT_QUERY;
    }

    private Result getInsertResult() {

        Result result = new Result();

        result.setId(1);
        result.setHorseId(20);
        result.setRaceId(20);
        result.setPlace(1);

        return result;

    }

    @Test
    public void delRecord() throws SQLException {

        String insertResultForDeleteQuery = getInsertResultForDeleteQuery();

        executeQuery(insertResultForDeleteQuery);

        String deleteQuery = getDeleteQuery();
        PreparedStatement preparedStatement = getPreparedStatement(deleteQuery);
        Result result = getDeleteResult();

        TestResultDaoImpl testDao = getTestDao();
        testDao.delRecord(preparedStatement, result);

        String deleteExistingResultQuery = getDeleteExistingResultQuery();

        int value = getActualNumRowsAffected(deleteExistingResultQuery);
        assertEquals(value, 0);

    }

    private String getInsertResultForDeleteQuery() {
        return INSERT_RESULT_FOR_DELETE_QUERY;
    }

    private Result getDeleteResult() {

        Result result = new Result();

        result.setId(2);
        result.setHorseId(20);
        result.setRaceId(20);
        result.setPlace(2);

        return result;

    }

    private void executeQuery(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

    }

    @Test
    public void updRecord() throws SQLException {

        String insertResultForUpdateQuery = getInsertResultForUpdateQuery();

        executeQuery(insertResultForUpdateQuery);

        String updateQuery = getUpdateQuery();
        PreparedStatement preparedStatement = getPreparedStatement(updateQuery);
        Result result = getUpdateResult();
        TestResultDaoImpl resultDao = getTestDao();
        resultDao.updRecord(preparedStatement, result);

        String deleteUpdateResultQuery = getDeleteUpdateResultQuery();


        int value = getActualNumRowsAffected(deleteUpdateResultQuery);
        assertEquals(value, 1);

    }

    private static String getInsertResultForUpdateQuery() {
        return INSERT_RESULT_FOR_UPDATE_QUERY;
    }

    private String getDeleteUpdateResultQuery() {
        return DELETE_UPDATE_RESULT_QUERY;
    }


    @Test
    public void getRecords() throws SQLException {

        String insertPredictionForSelectQuery = getInsertResultForSelectQuery();
        String deletePredictionForSelectQuery = getDeleteResultForSelectQuery();

        executeQuery(insertPredictionForSelectQuery);
        ResultSet resultSet = getResultSet();
        executeQuery(deletePredictionForSelectQuery);

        TestResultDaoImpl testDao = getTestDao();
        List<Result> results = testDao.getRecords(resultSet);

        Result result = results.get(0);



        int resultId = result.getId();
        int horseId = result.getHorseId();
        int raceId = result.getRaceId();
        int place = result.getPlace();



        assertEquals(resultId, 4);
        assertEquals(horseId, 20);
        assertEquals(raceId, 20);
        assertEquals(place, 1);
    }



    private Result getUpdateResult() {

        Result result = new Result();

        result.setId(3);
        result.setRaceId(20);
        result.setHorseId(20);
        result.setPlace(5);

        return result;
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


    private TestResultDaoImpl getTestDao() {
        return new TestResultDaoImpl();
    }

    private String getInsertQuery() {
        return INSERT_QUERY;
    }

    private String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    private String getDeleteQuery() {
        return DELETE_QUERY;
    }

    private String getUpdateQuery() {
        return UPDATE_QUERY;
    }
}
