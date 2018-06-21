package com.epam.horseraces.dao.impl;

import com.epam.horseraces.dao.QuotationDao;
import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.entity.enums.ResultType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code QuotationDaoImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class QuotationDaoImpl extends AbstractGeneralDaoImpl<Quotation> implements QuotationDao {

    private static final String INSERT_QUERY = "insert into quotations(user_id,race_id,horse_id,qoutate_description,coefficient) values(?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient,is_victorious from quotations where is_victorious = 0";
    private static final String DELETE_QUERY = "delete from quotations where quotation_id = ?";
    private static final String UPDATE_QUERY = "update quotations set horse_id = ?, race_id = ?, qoutate_description = ?, coefficient = ? where quotation_id = ?";
    private static final String SELECT_BY_ID_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient, is_victorious from quotations where user_id = ? and is_victorious = 0";
    private static final String CHANGE_STATUS_QUERY = "update quotations set is_victorious = ? where quotation_id = ?";
    private static final String TBD_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient,is_victorious from quotations";
    private static final String HISTORY_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient,is_victorious from quotations where user_id = ? and is_victorious != 0";
    private static final String EMPTY_LIST = "The list size is 0";


    /**
     * @return The insert query
     */
    @Override
    public String getInsertQuery() {
        return INSERT_QUERY;
    }

    /**
     * @return The update query
     */
    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    /**
     * @return The select query
     */
    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    /**
     * @return The delete query
     */
    @Override
    public String getDeleteQuery() {
        return DELETE_QUERY;
    }

    /**
     * @return The select by id query
     */
    private String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    /**
     * @return The change status query
     */
    private String getChangeStatusQuery() {
        return CHANGE_STATUS_QUERY;
    }

    /**
     * @return The select tbd query
     */

    private String getTBDQuery() {
        return TBD_QUERY;
    }

    /**
     * @return The history select query
     */
    private String getHistoryQuery() {
        return HISTORY_QUERY;
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          change status query
     * @param object            The appropriate object which status should
     *                          be changed
     * @throws SQLException If the query was competed with errors
     */
    private void changeStatus(PreparedStatement preparedStatement, Quotation object) throws SQLException {

        Integer id = object.getId();

        ResultType resultType = object.getResultType();
        Integer resultTypeId = resultType.getId();

        preparedStatement.setInt(1, resultTypeId);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();

    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          insert query
     * @param object            The appropriate object that should
     *                          be added
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void insertRecord(PreparedStatement preparedStatement, Quotation object) throws SQLException {

        Integer user_id = object.getUserId();
        Integer race_id = object.getRaceId();
        Integer horse_id = object.getHorseId();
        String description = object.getQuotateDescription();
        Double coefficient = object.getCoefficient();

        preparedStatement.setInt(1, user_id);
        preparedStatement.setInt(2, race_id);
        preparedStatement.setInt(3, horse_id);
        preparedStatement.setString(4, description);
        preparedStatement.setDouble(5, coefficient);

        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Quotation>} objects
     * @throws SQLException If the query was competed with errors
     */
    public List<Quotation> getRecordsId(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Quotation>} objects
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public List<Quotation> getRecords(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          delete query
     * @param object            The appropriate object that should
     *                          be deleted
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void deleteRecord(PreparedStatement preparedStatement, Quotation object) throws SQLException {
        Integer id = object.getId();
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    /**
     * @param preparedStatement Statement which is used for making the
     *                          update query
     * @param object            The appropriate object that should
     *                          be modified
     * @throws SQLException If the query was competed with errors
     */
    @Override
    public void updateRecord(PreparedStatement preparedStatement, Quotation object) throws SQLException {

        Integer id = object.getId();
        Integer race_id = object.getRaceId();
        Integer horse_id = object.getHorseId();
        String description = object.getQuotateDescription();
        Double coefficient = object.getCoefficient();

        preparedStatement.setInt(1, horse_id);
        preparedStatement.setInt(2, race_id);
        preparedStatement.setString(3, description);
        preparedStatement.setDouble(4, coefficient);
        preparedStatement.setInt(5, id);


        preparedStatement.executeUpdate();
    }

    /**
     * @param resultSet ResultSet which is used for making the
     *                  select query
     * @return The list of {@code <Quotation>} objects
     * @throws SQLException If the query was competed with errors
     */
    private List<Quotation> makeList(ResultSet resultSet) throws SQLException {
        List<Quotation> quotations = new ArrayList<Quotation>();
        while (resultSet.next()) {


            Integer id = resultSet.getInt(1);
            Integer user_id = resultSet.getInt(2);
            Integer race_id = resultSet.getInt(3);
            Integer horse_id = resultSet.getInt(4);
            String description = resultSet.getString(5);
            Double coefficient = resultSet.getDouble(6);
            Integer resultTypeId = resultSet.getInt(7);
            ResultType resultType = ResultType.getById(resultTypeId);


            Quotation quotation = new Quotation();


            quotation.setId(id);
            quotation.setUserId(user_id);
            quotation.setRaceId(race_id);
            quotation.setHorseId(horse_id);
            quotation.setQuotateDescription(description);
            quotation.setCoefficient(coefficient);
            quotation.setResultType(resultType);
            quotations.add(quotation);
        }
        return quotations;
    }

    /**
     * @param id The user's identifier
     * @return The list of {@code <Quotation>} objects
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public List<Quotation> getHistoryRecords(Integer id) throws DaoException {

        ConnectionPool connectionPool = new ConnectionPool(false);
        Connection connection;

        List<Quotation> quotations;

        try {
            connection = connectionPool.retrieve();
            String query = getHistoryQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            quotations = getRecordsId(resultSet);
            if (quotations.size() == 0) {
                throw new DaoException(EMPTY_LIST);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return quotations;
    }

    /**
     * @return The list of {@code <Quotation>} objects
     * @throws DaoException If the query was competed with errors
     */
    public List<Quotation> getAllTBDRecords() throws DaoException {
        ConnectionPool connectionPool = new ConnectionPool(false);

        List<Quotation> types;
        String query = getTBDQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            types = getRecords(resultSet);
            if (types.size() == 0) {
                throw new DaoException(EMPTY_LIST);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return types;
    }

    /**
     * @param object The appropriate object which status should
     *               be changed
     * @throws DaoException If the query was competed with errors
     */
    @Override
    public void changeStatus(Quotation object) throws DaoException {
        ConnectionPool connectionPool = new ConnectionPool(false);

        String query = getChangeStatusQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            changeStatus(preparedStatement, object);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * @param id he user's identifier
     * @return The list of {@code <Quotation>} objects
     * @throws DaoException If the query was competed with errors
     */
    public List<Quotation> getRecordsById(Integer id) throws DaoException {
        ConnectionPool connectionPool = new ConnectionPool(false);
        List<Quotation> types;
        String query = getSelectByIdQuery();
        Connection connection;
        try {
            connection = connectionPool.retrieve();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            types = getRecordsId(resultSet);
            if (types.size() == 0) {
                throw new DaoException(EMPTY_LIST);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return types;
    }

}
