package com.epam.horseraces.dao.entity;

import java.io.Serializable;

/**
 * The {@code User} class that contains the information
 * about the users
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class User implements Serializable {

    /**
     * The user identifier
     */
    private Integer id;

    /**
     * The role identifier
     */
    private Integer roleId;

    /**
     * The user login
     */
    private String login;

    /**
     * The user password identifier
     */
    private String password;

    /**
     * The user e_mail
     */
    private String eMail;

    /**
     * The method sets the user id
     *
     * @param id The user identifier
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The method sets the role id
     *
     * @param roleId The role identifier
     */
    public void setRoleID(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * The method sets the login
     *
     * @param login The user login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * The method sets the password
     *
     * @param password The user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The method sets the e_mail
     *
     * @param eMail The user e_mail
     */
    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * The method returns the user id
     *
     * @return The user identifier
     */
    public Integer getId() {
        return id;
    }

    /**
     * The method returns the role id
     *
     * @return The role identifier
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * The method returns the login
     *
     * @return The user login
     */
    public String getLogin() {
        return login;
    }

    /**
     * The method returns the password
     *
     * @return The user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * The method returns the e_mail
     *
     * @return The user identifier
     */
    public String getEmail() {
        return eMail;
    }

    /**
     * The method makes the comparison
     * of two objects and returns the boolean result
     *
     * @param obj The {@code User} object that would be compared
     *            with existing {@code User} object
     * @return The boolean result of comparison
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return user.getLogin().equals(login) && user.getPassword().equals(password) && user.getEmail().equals(eMail);
    }

    /**
     * The method returns the user's object string equivalent
     *
     * @return The {@code User} object's parameters string
     * equivalent
     */
    @Override
    public String toString() {
        return "" + id + roleId + login + password + eMail;
    }
}
