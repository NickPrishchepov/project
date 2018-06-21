package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.ResultDao;
import com.epam.horseraces.dao.entity.Result;
import com.epam.horseraces.dao.impl.AbstractGeneralDaoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestResultDaoImpl extends TestAbstractGeneralDaoImpl<Result> implements ResultDao{

    private static final String INSERT_QUERY = "insert into results(horse_id,race_id,place) values(?,?,?)";
    private static final String SELECT_ALL_QUERY = "select result_id,horse_id,race_id,place from results";
    private static final String DELETE_QUERY = "delete from results where result_id = ?";
    private static final String UPDATE_QUERY = "update results set horse_id = ?, race_id = ?, place = ? where result_id = ?";

    @Override
    public String getTBDQuery() {
        return null;
    }

    @Override
    public void changingStatus(PreparedStatement preparedStatement, Result object) {

    }

    @Override
    public String getChangeStatusQuery() {
        return null;
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
    public void insertRecord(PreparedStatement preparedStatement, Result object) throws SQLException{

        Integer horseId = object.getHorseId();
        Integer raceId = object.getRaceId();
        Integer place = object.getPlace();

        preparedStatement.setInt(1, horseId);
        preparedStatement.setInt(2,raceId);
        preparedStatement.setInt(3,place);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Result> getRecords(ResultSet resultSet) throws SQLException {
        List<Result> results = new ArrayList<Result>();
        while (resultSet.next()){

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

    @Override
    public void delRecord(PreparedStatement preparedStatement, Result object) throws SQLException {
        Integer resultId = object.getId();
        preparedStatement.setInt(1,resultId);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updRecord(PreparedStatement preparedStatement, Result object) throws SQLException {

        Integer id = object.getId();
        Integer horseId = object.getHorseId();
        Integer raceId = object.getRaceId();
        Integer place = object.getPlace();

        preparedStatement.setInt(1,horseId);
        preparedStatement.setInt(2,raceId);
        preparedStatement.setInt(3,place);
        preparedStatement.setInt(4,id);

        preparedStatement.executeUpdate();
    }

    @Override
    public void insertListResults(PreparedStatement preparedStatement,List<Result> results) {

        for(Result result : results) {
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

    @Override
    public List<Result> getRecordsById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Result> getRecordsId(ResultSet resultSet) {
        throw new UnsupportedOperationException();
    }
}
