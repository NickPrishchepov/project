package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.UserDao;
import com.epam.horseraces.dao.entity.User;
import com.epam.horseraces.dao.impl.AbstractGeneralDaoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TestUserDaoImpl extends TestAbstractGeneralDaoImpl<User> implements UserDao {

    private static final String INSERT_QUERY = "insert into users(role_id,login,password,e_mail) values(?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select user_id,role_id,login,password,e_mail from users";
    private static final String DELETE_QUERY = "delete from users where user_id = ?";
    private static final String UPDATE_QUERY = "update users set role_id = ?, login = ?, password = ?, e_mail = ? where user_id = ?";

    @Override
    public String getTBDQuery() {
        return null;
    }

    @Override
    public void changingStatus(PreparedStatement preparedStatement, User object) {

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

    @Override
    public void delRecord(PreparedStatement preparedStatement, User object) throws SQLException {
        Integer id = object.getId();
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updRecord(PreparedStatement preparedStatement, User object) throws SQLException {
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

    @Override
    public List<User> getRecordsById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getRecordsId(ResultSet resultSet) {
        throw new UnsupportedOperationException();
    }
}
