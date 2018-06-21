package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.entity.Race;
import com.epam.horseraces.dao.entity.enums.RaceType;
import com.epam.horseraces.dao.impl.classes.TestRaceDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;
import org.junit.Test;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RaceDaoImplTest {

    private static ConnectionPool connectionPool = new ConnectionPool(true);

    private static final String INSERT_QUERY = "insert into races(date,location,type) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select race_id,date,location,type,is_finished from races where is_finished = 0";
    private static final String DELETE_QUERY = "delete from races where race_id = ?";
    private static final String UPDATE_QUERY = "update races set date = ?, location = ?, type = ? where race_id = ?";

    private static final String DELETE_INSERT_RACE_QUERY = "delete from races where location = 'Madrid' and type = 1";
    private static final String INSERT_RACE_FOR_DELETE_QUERY = "insert into races(race_id,date,location,type) values(2,'2018-02-02','Monaco',2)";
    private static final String DELETE_UPDATE_RACE_QUERY = "delete from races where race_id = 3 and location = 'Brest'";

    private static final String INSERT_RACE_FOR_UPDATE_QUERY = "insert into races(race_id,date,location,type) values(3,'2020-02-02','Minsk',1)";

    private static final String INSERT_RACE_FOR_SELECT_QUERY = "insert into races(race_id,date,location,type) values(4,'2020-05-07','Manchester',1)";
    private static final String DELETE_RACE_FOR_SELECT_QUERY = "delete from races where race_id = 4 and location = 'Manchester'";
    private static final String DELETE_EXISTING_RACE_QUERY = "delete from races where race_id = 2 and location = 'Monaco' and date = '2018-02-02'";


    private String getInsertRaceForSelectQuery(){
        return INSERT_RACE_FOR_SELECT_QUERY;
    }

    private static String getDeleteRaceForSelectQuery() {
        return DELETE_RACE_FOR_SELECT_QUERY;
    }



    private String getDeleteExistingRaceQuery() {
        return DELETE_EXISTING_RACE_QUERY;
    }

    @Test
    public void insertRecord() throws SQLException {

        String insertQuery = getInsertQuery();
        PreparedStatement preparedStatement = getPreparedStatement(insertQuery);

        Race race = getInsertRace();

        TestRaceDaoImpl testDao = getTestDao();
        testDao.insertRecord(preparedStatement,race);

        String deleteInsertRaceQuery = getDeleteInsertRaceQuery();

        Integer actualResultSetSize = getActualNumRowsAffected(deleteInsertRaceQuery);
        Integer expectedResultSetSize = 1;

        assertEquals(actualResultSetSize,expectedResultSetSize);

    }

    private String getDeleteInsertRaceQuery(){
        return DELETE_INSERT_RACE_QUERY;
    }

    private Race getInsertRace(){

        Race race = new Race();
        race.setId(1);

        java.sql.Date date = new Date(new java.util.Date().getTime());

        RaceType raceType = RaceType.FLAT_RACING;

        race.setDate(date);
        race.setRaceType(raceType);
        race.setLocation("Madrid");

        return race;

    }

    @Test
    public void delRecord() throws SQLException, ParseException {

        String insertRaceForDeleteQuery = getInsertRaceForDeleteQuery();

        executeQuery(insertRaceForDeleteQuery);

        String deleteQuery = getDeleteQuery();
        PreparedStatement preparedStatement = getPreparedStatement(deleteQuery);
        Race race = getDeleteRace();

        TestRaceDaoImpl testDao = getTestDao();
        testDao.delRecord(preparedStatement,race);

        String deleteExistingRaceQuery = getDeleteExistingRaceQuery();

        int value = getActualNumRowsAffected(deleteExistingRaceQuery);
        assertEquals(value,0);

    }

    private String getInsertRaceForDeleteQuery(){
        return INSERT_RACE_FOR_DELETE_QUERY;
    }

    private Race getDeleteRace() throws ParseException {

        Race race = new Race();
        race.setId(2);
        java.util.Date utilDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "02.02.2018" );
        java.sql.Date date = new Date(utilDate.getTime());
        race.setLocation("Monaco");
        race.setDate(date);
        RaceType raceType = RaceType.STEEPLECHASE;
        race.setRaceType(raceType);

        return race;

    }

    private void executeQuery(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

    }

    @Test
    public void updRecord() throws SQLException {

        String insertRaceForUpdateQuery = getInsertRaceForUpdateQuery();

        executeQuery(insertRaceForUpdateQuery);

        String updateQuery = getUpdateQuery();
        PreparedStatement preparedStatement = getPreparedStatement(updateQuery);
        Race race = getUpdateRace();
        TestRaceDaoImpl testDao = getTestDao();
        testDao.updRecord(preparedStatement,race);

        String deleteUpdateRaceQuery = getDeleteUpdateRaceQuery();


        int value = getActualNumRowsAffected(deleteUpdateRaceQuery);
        assertEquals(value,1);

    }
    private static String getInsertRaceForUpdateQuery() {
        return INSERT_RACE_FOR_UPDATE_QUERY;
    }

    private String getDeleteUpdateRaceQuery(){
        return DELETE_UPDATE_RACE_QUERY;
    }


    @Test
    public void getRecords() throws SQLException {

        String insertRaceForSelectQuery = getInsertRaceForSelectQuery();
        String deleteRaceForSelectQuery = getDeleteRaceForSelectQuery();

        executeQuery(insertRaceForSelectQuery);
        ResultSet resultSet = getResultSet();
        executeQuery(deleteRaceForSelectQuery);

        TestRaceDaoImpl testDao = getTestDao();
        List<Race> races = testDao.getRecords(resultSet);

        Race race = races.get(0);

        int id = race.getId();
        java.util.Date date = race.getDate();
        String location = race.getLocation();
        RaceType raceType = race.getRaceType();

        RaceType expectedRaceType = RaceType.FLAT_RACING;

        assertEquals(id,4);
        assertEquals(date.toString(),"2020-05-07");
        assertEquals(location,"Manchester");
        assertEquals(raceType,expectedRaceType);

    }

    private Race getUpdateRace(){

        Race race = new Race();
        race.setId(3);

        java.sql.Date date = new Date(new java.util.Date().getTime());

        RaceType raceType = RaceType.HARNESS_RACING;

        race.setDate(date);
        race.setRaceType(raceType);
        race.setLocation("Brest");

        return race;
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


    private TestRaceDaoImpl getTestDao(){
        return new TestRaceDaoImpl();
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