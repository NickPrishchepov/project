package com.epam.horseraces.dao.exception;

/**
 * The {@code DaoException} class describes the exceptions
 * which could have been thrown while program running
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class DaoException extends Exception {

    /**
     * The constructor with no parameters
     */
    public DaoException() {
    }

    /**
     * The constructor with the message parameter
     *
     * @param message Exception message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * The constructor with two parameters
     *
     * @param message Exception message
     * @param cause   Exception cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor with cause parameter
     *
     * @param cause Exception cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }


}
