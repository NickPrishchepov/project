package com.epam.horseraces.dao.impl.classes;

import com.epam.horseraces.dao.QuotationDao;
import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.entity.enums.ResultType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.AbstractGeneralDaoImpl;
import com.epam.horseraces.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestQuotationDaoImpl extends TestAbstractGeneralDaoImpl<Quotation> implements QuotationDao {

    private static final String INSERT_QUERY = "insert into quotations(user_id,race_id,horse_id,qoutate_description,coefficient) values(?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient,is_victorious from quotations where is_victorious = 0";
    private static final String DELETE_QUERY = "delete from quotations where quotation_id = ?";
    private static final String UPDATE_QUERY = "update quotations set horse_id = ?, race_id = ?, qoutate_description = ?, coefficient = ? where quotation_id = ?";
    private static final String SELECT_BY_ID_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient, is_victorious from quotations where user_id = ? and is_victorious = 0";
    private static final String CHANGE_STATUS_QUERY = "update quotations set is_victorious = ? where quotation_id = ?";
    private static final String TBD_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient,is_victorious from quotations";
    private static final String HISTORY_QUERY = "select quotation_id,user_id,race_id,horse_id,qoutate_description,coefficient,is_victorious from quotations where user_id = ? and is_victorious != 0";

    @Override
    public String getTBDQuery() {
        return TBD_QUERY;
    }

    private String getHistoryQuery(){
        return HISTORY_QUERY;
    }

    @Override
    public String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    public String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    public String getChangeStatusQuery() {
        return CHANGE_STATUS_QUERY;
    }

    @Override
    public void changingStatus(PreparedStatement preparedStatement, Quotation object) throws SQLException {

        Integer id = object.getId();

        ResultType resultType = object.getResultType();
        Integer resultTypeId = resultType.getId();

        preparedStatement.setInt(1, resultTypeId);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();

    }

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

    public List<Quotation> getRecordsId(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    @Override
    public List<Quotation> getRecords(ResultSet resultSet) throws SQLException {
        return makeList(resultSet);
    }

    @Override
    public void delRecord(PreparedStatement preparedStatement, Quotation object) throws SQLException {
        Integer id = object.getId();
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updRecord(PreparedStatement preparedStatement, Quotation object) throws SQLException {

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
                throw new DaoException("The list size is 0");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return quotations;
    }
}
