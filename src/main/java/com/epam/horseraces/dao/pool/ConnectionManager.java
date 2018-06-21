package com.epam.horseraces.dao.pool;

import java.sql.Connection;

/**
 * The {@code ConnectionManager} interface defines the methods
 * that installs the connection with the appropriate database source
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public interface ConnectionManager {

    /**
     * @return The existing connection with the database
     */
    Connection retrieve();

    /**
     * @param connection The existing connection with the database
     * @throws NullPointerException If it's impossible to return
     *                              the existing connection
     */
    void putback(Connection connection) throws NullPointerException;
}
