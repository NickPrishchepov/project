package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.entity.Horse;
import com.epam.horseraces.dao.impl.classes.TestHorseDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HorseDaoImplTest {

    private static ConnectionPool connectionPool = new ConnectionPool(true);

    private static final int FIRST = 0;
    private static final int EXPECTED_HORSE_ID = 4;
    private static final String EXPECTED_PARAMETER = "arr";
    private static final int EXPECTED_HORSE_AGE = 5;
    private static final double EXPECTED_RATING = 5.0;
    private static final double DELTA = 0.1;


    private static final int UPDATE_HORSE_ID = 3;
    private static final String UPDATE_HORSE_PARAMETER = "zhao";
    private static final int UPDATE_HORSE_AGE = 5;
    private static final double UPDATE_HORSE_RATING = 5.0;

    private static final int INSERT_HORSE_ID = 1;
    private static final String INSERT_HORSE_NICKNAME = "nickname";
    private static final String INSERT_HORSE_BREED = "breed";
    private static final int INSERT_HORSE_AGE = 1;
    private static final String INSERT_HORSE_JOCKEY_NAME = "jockeyName";
    private static final double INSERT_HORSE_RATING = 1.0;

    private static final int DELETE_HORSE_ID = 2;
    private static final String DELETE_HORSE_NICKNAME = "nickname";
    private static final String DELETE_HORSE_BREED = "breed";
    private static final int DELETE_HORSE_AGE = 3;
    private static final String DELETE_HORSE_JOCKEY = "jockey";
    private static final double DELETE_HORSE_RATING = 3.0;

    private static final String INSERT_QUERY = "insert into horses(nickname,breed,horse_age,jockey,rating) values(?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select horse_id,nickname,breed,horse_age,jockey,rating from horses";
    private static final String DELETE_QUERY = "delete from horses where horse_id = ?";
    private static final String UPDATE_QUERY = "update horses set nickname = ?, breed = ?, horse_age = ?, jockey = ?, rating = ? where horse_id = ?";

    private static final String DELETE_INSERT_HORSE_QUERY = "delete from horses where horse_age = 1 and nickname = 'nickname' and breed = 'breed'";
    private static final String INSERT_HORSE_FOR_DELETE_QUERY = "insert into horses(horse_id,nickname,breed,horse_age,jockey,rating) values(2,'nickname','breed',3,'jockey',3.0)";
    private static final String DELETE_UPDATE_HORSE_QUERY = "delete from horses where nickname = 'zhao' and rating = 5.0";

    private static final String INSERT_HORSE_FOR_UPDATE_QUERY = "insert into horses(horse_id,nickname,breed,horse_age,jockey,rating) values(3,'arrow','arrow',4,'arrow', 4.0)";

    private static final String INSERT_HORSE_FOR_SELECT_QUERY = "insert into horses(horse_id,nickname,breed,horse_age,jockey,rating) values(4,'arr','arr',5,'arr', 5.0)";

    private static final String DELETE_HORSE_FOR_SELECT_QUERY = "delete from horses where horse_id = 4 and rating = 5.0";
    private static final String DELETE_EXISTING_HORSE_QUERY = "delete from horses where horse_id = 2 and nickname = 'nickname' and breed = 'breed' and horse_age = 3 and jockey = 'jockey' and rating = 3.0";

    private String getInsertHorseForSelectQuery(){
        return INSERT_HORSE_FOR_SELECT_QUERY;
    }

    private String getDeleteExistingHorseQuery() {
        return DELETE_EXISTING_HORSE_QUERY;
    }

    private static String getDeleteHorseForSelectQuery() {
        return DELETE_HORSE_FOR_SELECT_QUERY;
    }

    @Test
    public void insertRecord() throws SQLException {

        String insertQuery = getInsertQuery();
        PreparedStatement preparedStatement = getPreparedStatement(insertQuery);

        Horse horse = getInsertHorse();

        TestHorseDaoImpl horseDao = getTestHorseDaoImpl();
        horseDao.insertRecord(preparedStatement,horse);

        String deleteInsertHorseQuery = getDeletingInsertHorseQuery();

        Integer actualResultSetSize = getActualNumRowsAffected(deleteInsertHorseQuery);
        Integer expectedResultSetSize = 1;

        assertEquals(actualResultSetSize,expectedResultSetSize);

    }

    @Test
    public void delRecord() throws SQLException {

        String insertHorseForDeleteQuery = getInsertHorseForDeleteQuery();

        executeQuery(insertHorseForDeleteQuery);

        String deleteQuery = getDeleteQuery();
        PreparedStatement preparedStatement = getPreparedStatement(deleteQuery);
        Horse horse = getDeleteHorse();

        TestHorseDaoImpl horseDao = getTestHorseDaoImpl();
        horseDao.delRecord(preparedStatement,horse);

        String deleteExistingHorseQuery = getDeleteExistingHorseQuery();

        int value = getActualNumRowsAffected(deleteExistingHorseQuery);
        assertEquals(value,0);

    }

    private Horse getDeleteHorse(){

        Horse horse = new Horse();

        horse.setId(DELETE_HORSE_ID);
        horse.setNickname(DELETE_HORSE_NICKNAME);
        horse.setBreed(DELETE_HORSE_BREED);
        horse.setAge(DELETE_HORSE_AGE);
        horse.setJockeyName(DELETE_HORSE_JOCKEY);
        horse.setRating(DELETE_HORSE_RATING);

        return horse;

    }

    private String getInsertHorseForDeleteQuery(){
        return INSERT_HORSE_FOR_DELETE_QUERY;
    }

    private void executeQuery(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

    }

    @Test
    public void updRecord() throws SQLException {

        String insertHorseForUpdateQuery = getInsertHorseForUpdateQuery();

        executeQuery(insertHorseForUpdateQuery);

        String updateQuery = getUpdateQuery();
        PreparedStatement preparedStatement = getPreparedStatement(updateQuery);
        Horse horse = getUpdateHorse();
        TestHorseDaoImpl horseDao = getTestHorseDaoImpl();
        horseDao.updRecord(preparedStatement,horse);

        String deleteUpdateHorseQuery = getDeleteUpdateHorseQuery();

        int value = getActualNumRowsAffected(deleteUpdateHorseQuery);
        assertEquals(value,1);

    }

    @Test
    public void getRecords() throws SQLException {

        String insertHorseForSelectQuery = getInsertHorseForSelectQuery();
        String deleteHorseForSelectQuery = getDeleteHorseForSelectQuery();

        executeQuery(insertHorseForSelectQuery);
        ResultSet resultSet = getResultSet();
        executeQuery(deleteHorseForSelectQuery);

        TestHorseDaoImpl testHorseDao = getTestHorseDaoImpl();
        List<Horse> horses = testHorseDao.getRecords(resultSet);

        Horse horse = horses.get(FIRST);

        int id = horse.getId();
        String nickname = horse.getNickname();
        String breed = horse.getBreed();
        int age = horse.getAge();
        String jockeyname = horse.getJockeyName();
        double rating = horse.getRating();

        assertEquals(id, EXPECTED_HORSE_ID);
        assertEquals(nickname, EXPECTED_PARAMETER);
        assertEquals(breed,EXPECTED_PARAMETER);
        assertEquals(age, EXPECTED_HORSE_AGE);
        assertEquals(jockeyname,EXPECTED_PARAMETER);
        assertEquals(rating, EXPECTED_RATING, DELTA);

    }

    private Horse getUpdateHorse(){

        Horse horse = new Horse();

        horse.setId(UPDATE_HORSE_ID);
        horse.setNickname(UPDATE_HORSE_PARAMETER);
        horse.setBreed(UPDATE_HORSE_PARAMETER);
        horse.setAge(UPDATE_HORSE_AGE);
        horse.setJockeyName(UPDATE_HORSE_PARAMETER);
        horse.setRating(UPDATE_HORSE_RATING);

        return horse;
    }

    private Horse getInsertHorse(){

        Horse horse = new Horse();

        horse.setId(INSERT_HORSE_ID);
        horse.setNickname(INSERT_HORSE_NICKNAME);
        horse.setBreed(INSERT_HORSE_BREED);
        horse.setAge(INSERT_HORSE_AGE);
        horse.setJockeyName(INSERT_HORSE_JOCKEY_NAME);
        horse.setRating(INSERT_HORSE_RATING);

        return horse;
    }

    private Integer getActualNumRowsAffected(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);

    }

    private String getDeletingInsertHorseQuery(){
        return DELETE_INSERT_HORSE_QUERY;
    }


    private String getDeleteUpdateHorseQuery(){
        return DELETE_UPDATE_HORSE_QUERY;
    }


    private static String getInsertHorseForUpdateQuery() {
        return INSERT_HORSE_FOR_UPDATE_QUERY;
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


    private TestHorseDaoImpl getTestHorseDaoImpl(){
        return new TestHorseDaoImpl();
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