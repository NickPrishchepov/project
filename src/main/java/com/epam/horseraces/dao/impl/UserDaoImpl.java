package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.UserDao;
import com.epam.horseraces.dao.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code UserDaoImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class UserDaoImpl extends AbstractGeneralDaoImpl<User> implements UserDao {

    private static final String INSERT_QUERY = "insert into users(role_id,login,password,e_mail) values(?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select user_id,role_id,login,password,e_mail from users";
    private static final String DELETE_QUERY = "delete from users where user_id = ?";
    private static final String UPDATE_QUERY = "update users set role_id = ?, login = ?, password = ?, e_mail = ? where user_id = ?";

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
    public void insertRecord(PreparedStatement preparedStatement, User object) throws SQLException {
        Integer roleId = object.getRoleId();
        String login = object.getLogin();
        String password = object.getPassword();
        String email = object.getEmail();
        preparedStatement.setInt(1, roleId);
        preparedStatement.setString(2, login);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, email);
        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <User>} objects
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public List<User> getRecords(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<User>();
        while (resultSet.next()) {
            User user = new User();
            Integer id = resultSet.getInt(1);
            Integer roleId = resultSet.getInt(2);
            String login = resultSet.getString(3);
            String password = resultSet.getString(4);
            String email = resultSet.getString(5);
            user.setId(id);
            user.setRoleID(roleId);
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            users.add(user);
        }
        return users;
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          delete query
     * @param object            The appropriate object that should
     *                          be deleted
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void deleteRecord(PreparedStatement preparedStatement, User object) throws SQLException {
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
    public void updateRecord(PreparedStatement preparedStatement, User object) throws SQLException {
        Integer id = object.getId();
        Integer roleId = object.getRoleId();
        String login = object.getLogin();
        String password = object.getPassword();
        String email = object.getEmail();
        preparedStatement.setInt(1, roleId);
        preparedStatement.setString(2, login);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, email);
        preparedStatement.setInt(5, id);
        preparedStatement.executeUpdate();
    }

}
