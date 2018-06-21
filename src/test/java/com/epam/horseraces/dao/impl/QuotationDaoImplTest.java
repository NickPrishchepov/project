package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.impl.classes.TestQuotationDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuotationDaoImplTest {

    private static ConnectionPool connectionPool = new ConnectionPool(true);

    private static final String INSERT_QUERY = "insert into quotations(user_id,race_id,horse_id,qoutate_description,coefficient) values(?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient,is_victorious from quotations where is_victorious = 0";
    private static final String DELETE_QUERY = "delete from quotations where quotation_id = ?";
    private static final String UPDATE_QUERY = "update quotations set horse_id = ?, race_id = ?, qoutate_description = ?, coefficient = ? where quotation_id = ?";

    private static final String DELETE_INSERT_QUOTATION_QUERY = "delete from quotations where coefficient = 8.0 and horse_id = 20 and qoutate_description = 'WIN'";
    private static final String INSERT_QUOTATION_FOR_DELETE_QUERY = "insert into quotations(quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient) values(1,20,20,20,'UNDERDOG',3.0)";
    private static final String DELETE_UPDATE_QUOTATION_QUERY = "delete from quotations where qoutate_description = 'WIN' and coefficient = 9.0";

    private static final String INSERT_QUOTATION_FOR_UPDATE_QUERY = "insert into quotations(quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient) values(3,20,20,20,'FIRST_THREE', 7.0)";

    private static final String INSERT_QUOTATION_FOR_SELECT_QUERY = "insert into quotations(quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient) values(4,20,20,20,'WIN', 10.0)";
    private static final String DELETE_QUOTATION_FOR_SELECT_QUERY = "delete from quotations where horse_id = 20 and coefficient = 10.0";

    private String getInsertQuotationForSelectQuery(){
        return INSERT_QUOTATION_FOR_SELECT_QUERY;
    }

    private static String getDeleteQuotationForSelectQuery() {
        return DELETE_QUOTATION_FOR_SELECT_QUERY;
    }

    private static final String DELETE_EXISTING_QUOTATION_QUERY = "delete from quotations where quotation_id = 1 and race_id = 20 and horse_id = 20 and user_id = 20 and qoutate_description = 'UNDERDOG' and coefficient = 3.0";


    private String getDeleteExistingQuotationQuery() {
        return DELETE_EXISTING_QUOTATION_QUERY;
    }

    @Test
    public void insertRecord() throws SQLException {

        String insertQuery = getInsertQuery();
        PreparedStatement preparedStatement = getPreparedStatement(insertQuery);

        Quotation quotation = getInsertQuotation();

        TestQuotationDaoImpl testDao = getTestDao();
        testDao.insertRecord(preparedStatement,quotation);

        String deleteInsertQuotationQuery = getDeleteInsertQuotationQuery();

        Integer actualResultSetSize = getActualNumRowsAffected(deleteInsertQuotationQuery);
        Integer expectedResultSetSize = 1;

        assertEquals(actualResultSetSize,expectedResultSetSize);

    }

    private String getDeleteInsertQuotationQuery(){
        return DELETE_INSERT_QUOTATION_QUERY;
    }

    private Quotation getInsertQuotation(){

        Quotation quotation = new Quotation();

        quotation.setRaceId(20);
        quotation.setHorseId(20);
        quotation.setUserId(20);
        quotation.setQuotateDescription("WIN");
        quotation.setCoefficient(8.0);

        return quotation;

    }

    @Test
    public void delRecord() throws SQLException {

        String insertQuotationForDeleteQuery = getInsertQuotationForDeleteQuery();

        executeQuery(insertQuotationForDeleteQuery);

        String deleteQuery = getDeleteQuery();
        PreparedStatement preparedStatement = getPreparedStatement(deleteQuery);
        Quotation quotation = getDeleteQuotation();



        TestQuotationDaoImpl horseDao = getTestDao();
        horseDao.delRecord(preparedStatement,quotation);

        String deleteExistingQuotationQuery = getDeleteExistingQuotationQuery();

        int value = getActualNumRowsAffected(deleteExistingQuotationQuery);
        assertEquals(value,0);

    }

    private String getInsertQuotationForDeleteQuery(){
        return INSERT_QUOTATION_FOR_DELETE_QUERY;
    }

    private Quotation getDeleteQuotation(){

        Quotation quotation = new Quotation();

        quotation.setId(1);
        quotation.setUserId(20);
        quotation.setRaceId(20);
        quotation.setHorseId(20);
        quotation.setQuotateDescription("UNDERDOG");
        quotation.setCoefficient(3.0);

        return quotation;

    }

    private void executeQuery(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

    }

    @Test
    public void updRecord() throws SQLException {

        String insertQuotationForUpdateQuery = getInsertQuotationForUpdateQuery();

        executeQuery(insertQuotationForUpdateQuery);

        String updateQuery = getUpdateQuery();
        PreparedStatement preparedStatement = getPreparedStatement(updateQuery);
        Quotation quotation = getUpdateQuotation();
        TestQuotationDaoImpl horseDao = getTestDao();
        horseDao.updRecord(preparedStatement,quotation);

        String deleteUpdateQuotationQuery = getDeleteUpdateQuotationQuery();


        int value = getActualNumRowsAffected(deleteUpdateQuotationQuery);
        assertEquals(value,1);

    }
    private static String getInsertQuotationForUpdateQuery() {
        return INSERT_QUOTATION_FOR_UPDATE_QUERY;
    }

    private String getDeleteUpdateQuotationQuery(){
        return DELETE_UPDATE_QUOTATION_QUERY;
    }


    @Test
    public void getRecords() throws SQLException {

        String insertQuotationForSelectQuery = getInsertQuotationForSelectQuery();
        String deleteQuotationForSelectQuery = getDeleteQuotationForSelectQuery();

        executeQuery(insertQuotationForSelectQuery);
        ResultSet resultSet = getResultSet();
        executeQuery(deleteQuotationForSelectQuery);

        TestQuotationDaoImpl testHorseDao = getTestDao();
        List<Quotation> quotations = testHorseDao.getRecords(resultSet);

        Quotation quotation = quotations.get(0);

        int id = quotation.getId();
        int user_id = quotation.getUserId();
        int race_id = quotation.getRaceId();
        int horse_id = quotation.getHorseId();
        String description = quotation.getQuotateDescription();
        double coefficient = quotation.getCoefficient();



        assertEquals(id,4);
        assertEquals(user_id,20);
        assertEquals(race_id,20);
        assertEquals(horse_id,20);
        assertEquals(description,"WIN");
        assertEquals(coefficient,10.0,0.1);

    }

    private Quotation getUpdateQuotation(){

        Quotation quotation = new Quotation();

        quotation.setId(3);
        quotation.setUserId(20);
        quotation.setRaceId(20);
        quotation.setHorseId(20);
        quotation.setQuotateDescription("WIN");
        quotation.setCoefficient(9.0);

        return quotation;
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


    private TestQuotationDaoImpl getTestDao(){
        return new TestQuotationDaoImpl();
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