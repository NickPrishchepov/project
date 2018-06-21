package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.HorseDao;
import com.epam.horseraces.dao.entity.Horse;
import com.epam.horseraces.dao.impl.AbstractGeneralDaoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestHorseDaoImpl extends TestAbstractGeneralDaoImpl<Horse> implements HorseDao {

    private static final String INSERT_QUERY = "insert into horses(nickname,breed,horse_age,jockey,rating) values(?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select horse_id,nickname,breed,horse_age,jockey,rating from horses";
    private static final String DELETE_QUERY = "delete from horses where horse_id = ?";
    private static final String UPDATE_QUERY = "update horses set nickname = ?, breed = ?, horse_age = ?, jockey = ?, rating = ? where horse_id = ?";

    @Override
    public void changingStatus(PreparedStatement preparedStatement, Horse object) {

    }

    @Override
    public String getTBDQuery() {
        return null;
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
    public void insertRecord(PreparedStatement preparedStatement, Horse object) throws SQLException {

        String nickname = object.getNickname();
        String breed = object.getBreed();
        Integer age = object.getAge();
        String jockeyname = object.getJockeyName();
        Double rating = object.getRating();

        preparedStatement.setString(1, nickname);
        preparedStatement.setString(2,breed);
        preparedStatement.setInt(3,age);
        preparedStatement.setString(4,jockeyname);
        preparedStatement.setDouble(5,rating);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Horse> getRecords(ResultSet resultSet) throws SQLException {
        List<Horse> horses = new ArrayList<Horse>();
        while (resultSet.next()){

            Integer id = resultSet.getInt(1);
            String nickname = resultSet.getString(2);
            String breed = resultSet.getString(3);
            Integer age = resultSet.getInt(4);
            String jockeyname = resultSet.getString(5);
            Double rating = resultSet.getDouble(6);

            Horse horse = new Horse();
            horse.setId(id);
            horse.setNickname(nickname);
            horse.setBreed(breed);
            horse.setAge(age);
            horse.setJockeyName(jockeyname);
            horse.setRating(rating);
            horses.add(horse);
        }
        return horses;
    }

    @Override
    public void delRecord(PreparedStatement preparedStatement, Horse object) throws SQLException {
        Integer id = object.getId();
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updRecord(PreparedStatement preparedStatement, Horse object) throws SQLException {

        Integer id = object.getId();
        String nickname = object.getNickname();
        String breed = object.getBreed();
        Integer age = object.getAge();
        String jockeyname = object.getJockeyName();
        Double rating = object.getRating();

        preparedStatement.setString(1, nickname);
        preparedStatement.setString(2,breed);
        preparedStatement.setInt(3,age);
        preparedStatement.setString(4,jockeyname);
        preparedStatement.setDouble(5,rating);
        preparedStatement.setInt(6,id);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Horse> getRecordsById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Horse> getRecordsId(ResultSet resultSet) {
        throw new UnsupportedOperationException();
    }
}
