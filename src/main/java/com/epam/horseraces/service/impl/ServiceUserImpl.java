package com.epam.horseraces.service.impl;

import com.epam.horseraces.dao.entity.User;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.UserDaoImpl;

import java.util.List;

/**
 * The {@code ServiceUserImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ServiceUserImpl {

    private static final String ADMIN = "admin";
    private static final String USER = "user";
    private static final String BOOKMAKER = "bookmaker";
    private static final int INCORRECT_ROLE = 0;
    private static final int ADMINISTRATOR = 1;
    private static final int ADMINISTRATOR_ID = ADMINISTRATOR;
    private static final int USER_ID = 2;
    private static final int BOOKMAKER_ID = 3;

    /**
     * @param login    The user login
     * @param password The user password
     * @param email    The user e_mail
     * @return The user wich such attributes
     * @throws DaoException If the user with such attributes wasn't founded
     */
    public User getUser(String login, String password, String email) throws DaoException {
        UserDaoImpl dao = new UserDaoImpl();
        List<User> users = dao.getAllRecords();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        User returnUser = null;
        for (User object : users) {
            if (user.equals(object)) {
                returnUser = object;
            }
        }
        return returnUser;
    }

    /**
     * @param login The user login
     * @return The user with such login
     * @throws DaoException If the query was competed with errors
     */
    public User getUserByLogin(String login) throws DaoException {
        User user = null;
        UserDaoImpl dao = new UserDaoImpl();
        List<User> users = dao.getAllRecords();
        for (User object : users) {
            if (object.getLogin().equals(login)) {
                user = object;
            }
        }
        return user;
    }

    /**
     * @param id The user id
     * @throws DaoException If the query was competed with errors
     */
    public void deleteUser(int id) throws DaoException {

        UserDaoImpl dao = new UserDaoImpl();
        User user = new User();
        user.setId(id);
        dao.deleteRecord(user);
    }

    /**
     * @param roleId   The role id
     * @param login    The user login
     * @param password The user password
     * @param email    The user e_mail
     * @throws DaoException If the user with such attributes wasn't founded
     */
    public void addUser(int roleId, String login, String password, String email) throws DaoException {

        User user = new User();
        user.setRoleID(roleId);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);

        UserDaoImpl dao = new UserDaoImpl();
        dao.addRecord(user);
    }

    /**
     * @param id       The user id
     * @param roleId   The role id
     * @param login    The login
     * @param password The password
     * @param email    The email
     * @throws DaoException If the query was competed with errors
     */
    public void editUser(int id, int roleId, String login, String password, String email) throws DaoException {

        User user = new User();
        user.setId(id);
        user.setRoleID(roleId);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);

        UserDaoImpl dao = new UserDaoImpl();
        dao.updateRecord(user);

    }

    /**
     * @param role The string form of role parameter
     * @return The role id
     */
    public int getRoleId(String role) {
        int roleId = INCORRECT_ROLE;
        if (ADMIN.equals(role)) {
            roleId = ADMINISTRATOR_ID;
        } else if (USER.equals(role)) {
            roleId = USER_ID;
        } else if (BOOKMAKER.equals(role)) {
            roleId = BOOKMAKER_ID;
        }
        return roleId;
    }


}
