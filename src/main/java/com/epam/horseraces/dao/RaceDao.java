package com.epam.horseraces.dao;

import com.epam.horseraces.dao.entity.Race;
import com.epam.horseraces.dao.exception.DaoException;

import java.util.List;

/**
 * The {@code RaceDao} interface provides some methods of
 * the work with the database source. All the classes using this interface
 * should implement the methods
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public interface RaceDao extends GeneralDao<Race> {

    /**
     * @return The list of {@code <Race>} objects
     * @throws DaoException If the query was competed with errors
     */
    List<Race> getHistoryRecords() throws DaoException;

    /**
     * @param object The appropriate object which status should
     *               be changed
     * @throws DaoException If the query was competed with errors
     */
    void changeStatus(Race object) throws DaoException;

}
