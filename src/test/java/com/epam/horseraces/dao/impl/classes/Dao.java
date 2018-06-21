package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.exception.DaoException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface Dao<Type extends Serializable> {

    void addRecord(Type object) throws SQLException, DaoException;
    List<Type> getAllRecords() throws SQLException, DaoException;
    void deleteRecord(Type object) throws SQLException, DaoException;
    void updateRecord(Type object) throws SQLException, DaoException;
    List<Type> getRecordsById(Integer id) throws SQLException, DaoException;
    void changeStatus(Type object) throws SQLException, DaoException;
    List<Type> getAllTBDRecords() throws DaoException;
}
