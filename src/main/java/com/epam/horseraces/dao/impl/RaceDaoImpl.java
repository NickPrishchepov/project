package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.RaceDao;
import com.epam.horseraces.dao.entity.Race;
import com.epam.horseraces.dao.entity.enums.Ending;
import com.epam.horseraces.dao.entity.enums.RaceType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code RaceDaoImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class RaceDaoImpl extends AbstractGeneralDaoImpl<Race> implements RaceDao {

    private static final String INSERT_QUERY = "insert into races(date,location,type) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select race_id,date,location,type,is_finished from races where is_finished = 0";
    private static final String DELETE_QUERY = "delete from races where race_id = ?";
    private static final String UPDATE_QUERY = "update races set date = ?, location = ?, type = ? where race_id = ?";
    private static final String CHANGE_STATUS_QUERY = "update races set is_finished = ? where race_id = ?";
    private static final String HISTORY_QUERY = "select race_id,date,location,type,is_finished from races where is_finished != 0";
    private static final String EMPTY_LIST = "The list size is 0";

    /**
     * @return The history select query
     */
    private String getHistoryQuery() {
        return HISTORY_QUERY;
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          change status query
     * @param object            The appropriate object which status should
     *                          be changed
     * @throws SQLException If the query was competed with errors
     */
    private void changeStatus(PreparedStatement preparedStatement, Race object) throws SQLException {

        Integer id = object.getId();
        Ending ending = object.getEnding();

        Integer endingId = ending.getId();

        preparedStatement.setInt(1, endingId);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    /**
     * @return The change status query
     */
    private String getChangeStatusQuery() {
        return CHANGE_STATUS_QUERY;
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
     * @param preparedStatement Statement which is used for making the
     *                          insert query
     * @param object            The appropriate object that should
     *                          be added
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void insertRecord(PreparedStatement preparedStatement, Race object) throws SQLException {

        Date date = new Date(object.getDate().getTime());
        String location = object.getLocation();
        RaceType raceType = object.getRaceType();
        Integer raceTypeId = raceType.getId();

        preparedStatement.setDate(1, date);
        preparedStatement.setString(2, location);
        preparedStatement.setInt(3, raceTypeId);
        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Race>} objects
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public List<Race> getRecords(ResultSet resultSet) throws SQLException {
        List<Race> races = new ArrayList<Race>();
        while (resultSet.next()) {
            Race race = new Race();
            Integer id = resultSet.getInt(1);
            Date date = resultSet.getDate(2);
            String location = resultSet.getString(3);
            Integer raceTypeId = resultSet.getInt(4);
            RaceType raceType = RaceType.getById(raceTypeId);

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

    /**
     * @param preparedStatement Statement which is used for making the
     *                          delete query
     * @param object            The appropriate object that should
     *                          be deleted
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void deleteRecord(PreparedStatement preparedStatement, Race object) throws SQLException {
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
    public void updateRecord(PreparedStatement preparedStatement, Race object) throws SQLException {
        Integer id = object.getId();
        Date date = new Date(object.getDate().getTime());
        String location = object.getLocation();

        RaceType raceType = object.getRaceType();
        Integer raceTypeId = raceType.getId();
        preparedStatement.setDate(1, date);
        preparedStatement.setString(2, location);
        preparedStatement.setInt(3, raceTypeId);
        preparedStatement.setInt(4, id);
        preparedStatement.executeUpdate();
    }

    /**
     * @param object The appropriate object which status should
     *               be changed
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public void changeStatus(Race object) throws DaoException {
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
     * @return The list of {@code <Race>} objects
     * @throws DaoException If the query was competed with errors
     */
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
                throw new DaoException(EMPTY_LIST);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return races;
    }
}
