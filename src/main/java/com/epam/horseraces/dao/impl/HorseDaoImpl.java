package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.HorseDao;
import com.epam.horseraces.dao.entity.Horse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code HorseDaoImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class HorseDaoImpl extends AbstractGeneralDaoImpl<Horse> implements HorseDao {

    private static final String INSERT_QUERY = "insert into horses(nickname,breed,horse_age,jockey,rating) values(?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select horse_id,nickname,breed,horse_age,jockey,rating from horses";
    private static final String DELETE_QUERY = "delete from horses where horse_id = ?";
    private static final String UPDATE_QUERY = "update horses set nickname = ?, breed = ?, horse_age = ?, jockey = ?, rating = ? where horse_id = ?";

    /**
     * The method returns the insert query
     *
     * @return The insert query
     */
    @Override
    public String getInsertQuery() {
        return INSERT_QUERY;
    }

    /**
     * The method returns the update query
     *
     * @return The update query
     */
    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    /**
     * The method returns the select query
     *
     * @return The select query
     */
    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    /**
     * The method returns the delete query
     *
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
    public void insertRecord(PreparedStatement preparedStatement, Horse object) throws SQLException {

        String nickname = object.getNickname();
        String breed = object.getBreed();
        Integer age = object.getAge();
        String jockeyname = object.getJockeyName();
        Double rating = object.getRating();

        preparedStatement.setString(1, nickname);
        preparedStatement.setString(2, breed);
        preparedStatement.setInt(3, age);
        preparedStatement.setString(4, jockeyname);
        preparedStatement.setDouble(5, rating);

        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Horses>} objects
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public List<Horse> getRecords(ResultSet resultSet) throws SQLException {
        List<Horse> horses = new ArrayList<Horse>();
        while (resultSet.next()) {

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

    /**
     * @param preparedStatement Statement which is used for making the
     *                          delete query
     * @param object            The appropriate object that should
     *                          be deleted
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void deleteRecord(PreparedStatement preparedStatement, Horse object) throws SQLException {
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
    public void updateRecord(PreparedStatement preparedStatement, Horse object) throws SQLException {

        Integer id = object.getId();
        String nickname = object.getNickname();
        String breed = object.getBreed();
        Integer age = object.getAge();
        String jockeyname = object.getJockeyName();
        Double rating = object.getRating();

        preparedStatement.setString(1, nickname);
        preparedStatement.setString(2, breed);
        preparedStatement.setInt(3, age);
        preparedStatement.setString(4, jockeyname);
        preparedStatement.setDouble(5, rating);
        preparedStatement.setInt(6, id);

        preparedStatement.executeUpdate();
    }

}
