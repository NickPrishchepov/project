package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.RaceDao;
import com.epam.horseraces.dao.entity.Race;
import com.epam.horseraces.dao.entity.enums.Ending;
import com.epam.horseraces.dao.entity.enums.RaceType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.AbstractGeneralDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestRaceDaoImpl extends TestAbstractGeneralDaoImpl<Race> implements RaceDao {

    private static final String INSERT_QUERY = "insert into races(date,location,type) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select race_id,date,location,type,is_finished from races where is_finished = 0";
    private static final String DELETE_QUERY = "delete from races where race_id = ?";
    private static final String UPDATE_QUERY = "update races set date = ?, location = ?, type = ? where race_id = ?";
    private static final String CHANGE_STATUS_QUERY = "update races set is_finished = ? where race_id = ?";
    private static final String HISTORY_QUERY = "select race_id,date,location,type,is_finished from races where is_finished != 0";



    @Override
    public String getTBDQuery() {
        return null;
    }

    private String getHistoryQuery() {
        return HISTORY_QUERY;
    }

    @Override
    public void changingStatus(PreparedStatement preparedStatement, Race object) throws SQLException {

        Integer id = object.getId();
        Ending ending = object.getEnding();

        Integer endingId = ending.getId();

        preparedStatement.setInt(1, endingId);
        preparedStatement.setInt(2,id);

        preparedStatement.executeUpdate();
    }


    @Override
    public String getChangeStatusQuery() {
        return CHANGE_STATUS_QUERY;
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
    public String getSelectByIdQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertRecord(PreparedStatement preparedStatement, Race object) throws SQLException{

        Date date = new Date(object.getDate().getTime());
        String location = object.getLocation();
        RaceType raceType = object.getRaceType();
        Integer raceTypeId = raceType.getId();
       // String type = object.getType();
        preparedStatement.setDate(1, date);
        preparedStatement.setString(2,location);
        preparedStatement.setInt(3,raceTypeId);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Race> getRecords(ResultSet resultSet) throws SQLException {
        List<Race> races = new ArrayList<Race>();
        while (resultSet.next()){
            Race race = new Race();
            Integer id = resultSet.getInt(1);
            Date date = resultSet.getDate(2);
            String location = resultSet.getString(3);
            Integer raceTypeId = resultSet.getInt(4);
            RaceType raceType = RaceType.getById(raceTypeId);
           // String type = resultSet.getString(4);
            Integer endingId = resultSet.getInt(5);
            Ending ending = Ending.getById(endingId);
            race.setId(id);
            race.setDate(date);
            race.setLocation(location);
            race.setRaceType(raceType);
            race.setEnding(ending);
            races.add(race);
        }
        return races;
    }

    @Override
    public void delRecord(PreparedStatement preparedStatement, Race object) throws SQLException {
        //Integer id = object.getId();
        //preparedStatement.setInt(1,id);
        //preparedStatement.executeUpdate();
    }

    @Override
    public void updRecord(PreparedStatement preparedStatement, Race object) throws SQLException {
        Integer id = object.getId();
        Date date = new Date(object.getDate().getTime());
        String location = object.getLocation();
        //String type = object.getType();
        RaceType raceType = object.getRaceType();
        Integer raceTypeId = raceType.getId();
        preparedStatement.setDate(1,date);
        preparedStatement.setString(2,location);
        preparedStatement.setInt(3,raceTypeId);
        preparedStatement.setInt(4,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Race> getRecordsById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Race> getRecordsId(ResultSet resultSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Race> getHistoryRecords() throws DaoException {

        ConnectionPool connectionPool = new ConnectionPool(false);
        Connection connection;

        List<Race> races;

        try {
            connection = connectionPool.retrieve();
            String query = getHistoryQuery();
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();
            races = getRecords(resultSet);
            if (races.size() == 0) {
                throw new DaoException("The list size is 0");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return races;
    }
}
