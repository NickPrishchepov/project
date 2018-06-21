package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.entity.User;
import com.epam.horseraces.dao.impl.classes.TestUserDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoImplTest {

    private static ConnectionPool connectionPool = new ConnectionPool(true);

    private static final String INSERT_QUERY = "insert into users(role_id,login,password,e_mail) values(?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select user_id,role_id,login,password,e_mail from users";
    private static final String DELETE_QUERY = "delete from users where user_id = ?";
    private static final String UPDATE_QUERY = "update users set role_id = ?, login = ?, password = ?, e_mail = ? where user_id = ?";

    private static final String DELETE_INSERT_USER_QUERY = "delete from users where role_id = 1 and login = 'login' and e_mail = 'e_mail'";
    private static final String INSERT_USER_FOR_DELETE_QUERY = "insert into users(user_id,role_id,login,password,e_mail) values(2,1,'admin','admin','admin')";
    private static final String DELETE_UPDATE_USER_QUERY = "delete from users where user_id = 3 and e_mail = 'user'";

    private static final String INSERT_USER_FOR_UPDATE_QUERY = "insert into users(user_id,role_id,login,password,e_mail) values(3,1,'user','user','us')";

    private static final String INSERT_USER_FOR_SELECT_QUERY = "insert into users(user_id,role_id,login,password,e_mail) values(4,1,'arrow','arrow','arrow')";
    private static final String DELETE_USER_FOR_SELECT_QUERY = "delete from users where user_id = 4 and login = 'arrow'";
    private static final String DELETE_EXISTING_USER_QUERY = "delete from users where user_id = 2 and role_id = 2 and login = 'admin' and password = 'admin' and e_mail = 'admin'";


    private String getInsertUserForSelectQuery(){
        return INSERT_USER_FOR_SELECT_QUERY;
    }

    private static String getDeleteUserForSelectQuery() {
        return DELETE_USER_FOR_SELECT_QUERY;
    }



    private String getDeleteExistingUserQuery() {
        return DELETE_EXISTING_USER_QUERY;
    }

    @Test
    public void insertRecord() throws SQLException {

        String insertQuery = getInsertQuery();
        PreparedStatement preparedStatement = getPreparedStatement(insertQuery);

        User user = getInsertUser();

        TestUserDaoImpl testDao = getTestDao();
        testDao.insertRecord(preparedStatement,user);

        String deleteInsertUserQuery = getDeleteInsertUserQuery();

        Integer actualResultSetSize = getActualNumRowsAffected(deleteInsertUserQuery);
        Integer expectedResultSetSize = 1;

        assertEquals(actualResultSetSize,expectedResultSetSize);

    }

    private String getDeleteInsertUserQuery(){
        return DELETE_INSERT_USER_QUERY;
    }

    private User getInsertUser(){

        User user = new User();
        user.setId(1);
        user.setRoleID(1);
        user.setLogin("login");
        user.setPassword("password");
        user.setEmail("e_mail");

        return user;

    }

    @Test
    public void delRecord() throws SQLException {

        String insertUserForDeleteQuery = getInsertUserForDeleteQuery();

        executeQuery(insertUserForDeleteQuery);

        String deleteQuery = getDeleteQuery();
        PreparedStatement preparedStatement = getPreparedStatement(deleteQuery);
        User user = getDeleteUser();



        TestUserDaoImpl testDao = getTestDao();
        testDao.delRecord(preparedStatement,user);

        String deleteExistingUserQuery = getDeleteExistingUserQuery();

        int value = getActualNumRowsAffected(deleteExistingUserQuery);
        assertEquals(value,0);

    }

    private String getInsertUserForDeleteQuery(){
        return INSERT_USER_FOR_DELETE_QUERY;
    }

    private User getDeleteUser(){

        User user = new User();
        user.setId(2);
        user.setRoleID(1);
        user.setLogin("admin");
        user.setPassword("admin");
        user.setEmail("admin");

        return user;

    }

    private void executeQuery(String query) throws SQLException {

        Connection connection = connectionPool.retrieve();
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

    }

    @Test
    public void updRecord() throws SQLException {

        String insertUserForUpdateQuery = getInsertUserForUpdateQuery();

        executeQuery(insertUserForUpdateQuery);

        String updateQuery = getUpdateQuery();
        PreparedStatement preparedStatement = getPreparedStatement(updateQuery);
        User user = getUpdateUser();
        TestUserDaoImpl testDao = getTestDao();
        testDao.updRecord(preparedStatement,user);

        String deleteUpdateUserQuery = getDeleteUpdateUserQuery();


        int value = getActualNumRowsAffected(deleteUpdateUserQuery);
        assertEquals(value,1);

    }
    private static String getInsertUserForUpdateQuery() {
        return INSERT_USER_FOR_UPDATE_QUERY;
    }

    private String getDeleteUpdateUserQuery(){
        return DELETE_UPDATE_USER_QUERY;
    }


    @Test
    public void getRecords() throws SQLException {

        String insertUserForSelectQuery = getInsertUserForSelectQuery();
        String deleteUserForSelectQuery = getDeleteUserForSelectQuery();

        executeQuery(insertUserForSelectQuery);
        ResultSet resultSet = getResultSet();
        executeQuery(deleteUserForSelectQuery);


        TestUserDaoImpl testDao = getTestDao();
        List<User> users = testDao.getRecords(resultSet);

        User user = users.get(0);

        int id = user.getId();
        int roleId = user.getRoleId();
        String login = user.getLogin();
        String password = user.getPassword();
        String email = user.getEmail();

        assertEquals(id,4);
        assertEquals(roleId,1);
        assertEquals(login,"arrow");
        assertEquals(password,"arrow");
        assertEquals(email,"arrow");

    }

    private User getUpdateUser(){

        User user = new User();

        user.setId(3);
        user.setRoleID(1);
        user.setLogin("user");
        user.setPassword("user");
        user.setEmail("user");

        return user;
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


    private TestUserDaoImpl getTestDao(){
        return new TestUserDaoImpl();
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