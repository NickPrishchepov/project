package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.ResultDao;
import com.epam.horseraces.dao.entity.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ResultDaoImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ResultDaoImpl extends AbstractGeneralDaoImpl<Result> implements ResultDao {

    private static final String INSERT_QUERY = "insert into results(horse_id,race_id,place) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select result_id,horse_id,race_id,place from results";
    private static final String DELETE_QUERY = "delete from results where result_id = ?";
    private static final String UPDATE_QUERY = "update results set horse_id = ?, race_id = ?, place = ? where result_id = ?";

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
    public void insertRecord(PreparedStatement preparedStatement, Result object) throws SQLException {

        Integer horseId = object.getHorseId();
        Integer raceId = object.getRaceId();
        Integer place = object.getPlace();

        preparedStatement.setInt(1, horseId);
        preparedStatement.setInt(2, raceId);
        preparedStatement.setInt(3, place);

        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Result>} objects
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public List<Result> getRecords(ResultSet resultSet) throws SQLException {
        List<Result> results = new ArrayList<Result>();
        while (resultSet.next()) {

            Integer resultId = resultSet.getInt(1);
            Integer horseId = resultSet.getInt(2);
            Integer raceId = resultSet.getInt(3);
            Integer place = resultSet.getInt(4);


            Result result = new Result();
            result.setId(resultId);
            result.setHorseId(horseId);
            result.setRaceId(raceId);
            result.setPlace(place);
            results.add(result);
        }
        return results;
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          delete query
     * @param object            The appropriate object that should
     *                          be deleted
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void deleteRecord(PreparedStatement preparedStatement, Result object) throws SQLException {
        Integer horseId = object.getHorseId();
        preparedStatement.setInt(1, horseId);
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
    public void updateRecord(PreparedStatement preparedStatement, Result object) throws SQLException {

        Integer horseId = object.getHorseId();
        Integer raceId = object.getRaceId();
        Integer place = object.getPlace();

        preparedStatement.setInt(1, raceId);
        preparedStatement.setInt(2, place);
        preparedStatement.setInt(3, horseId);

        preparedStatement.executeUpdate();
    }

    @Override
    public void insertListResults(PreparedStatement preparedStatement, List<Result> results) {

        for (Result result : results) {
            try {

                Integer horseId = result.getHorseId();
                Integer raceId = result.getRaceId();
                Integer place = result.getPlace();

                preparedStatement.setInt(1, horseId);
                preparedStatement.setInt(2, raceId);
                preparedStatement.setInt(3, place);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
