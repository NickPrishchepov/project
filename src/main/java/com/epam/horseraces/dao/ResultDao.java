package com.epam.horseraces.dao;

import com.epam.horseraces.dao.entity.Result;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * The {@code ResultDao} interface provides some methods of
 * the work with the database source. All the classes using this interface
 * should implement the methods
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public interface ResultDao extends GeneralDao<Result> {

    void insertListResults(PreparedStatement preparedStatement, List<Result> results);
}
