package com.epam.horseraces.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The {@code ConnectionPool} class installs the connection with
 * the appropriate database source. The connection could be installed
 * with the main database or test database
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ConnectionPool implements ConnectionManager {

    private static final String URL = "jdbc:mysql://localhost:3306/races?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String TEST_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static final Lock lock = new ReentrantLock();

    private static final int POOL_SIZE = 5;
    private static final String PUTBACK_CONNECTION_ERROR = "Connection is not in the used connections array";

    private static boolean isTesting;

    /**
     * The queue of the available connections
     */
    private Queue<Connection> available = new ArrayDeque<Connection>();
    /**
     * The queue of the used connections
     */
    private Queue<Connection> used = new ArrayDeque<Connection>();

    /**
     * @param testing The parameter that indicates if the test database
     *                is used or not
     */
    public ConnectionPool(boolean testing) {
        try {
            Class.forName(DRIVER);
            setTesting(testing);
            for (int counter = 0; counter < POOL_SIZE; counter++) {
                Connection connection = getConnection();
                available.add(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param testing The parameter that indicates if the test database
     *                is used or not
     */
    private static void setTesting(boolean testing) {
        isTesting = testing;
    }

    /**
     * @return The connection with existing database
     */
    private Connection getConnection() {
        Connection connection = null;
        try {
            if (isTesting) {
                connection = DriverManager.getConnection(TEST_URL, LOGIN, PASSWORD);
            } else {
                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * @return The existing connection with the database
     */
    public Connection retrieve() {
        Connection connection;
        lock.lock();
        if (available.size() == 0) {
            connection = getConnection();
        } else {
            connection = available.poll();
            available.remove(connection);
        }
        used.add(connection);
        lock.unlock();
        return connection;
    }

    /**
     * @param connection The existing connection with the database
     * @throws NullPointerException If it's impossible to return
     *                              the existing connection
     */
    public void putback(Connection connection) throws NullPointerException{
        try {
            lock.lock();

            if (connection != null) {
                if (used.remove(connection)) {
                    available.add(connection);
                } else {
                    throw new NullPointerException(PUTBACK_CONNECTION_ERROR);
                }
            }
        } finally {
            lock.unlock();
        }
    }
}