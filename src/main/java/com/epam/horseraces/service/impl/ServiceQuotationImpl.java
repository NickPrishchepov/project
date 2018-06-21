package com.epam.horseraces.service.impl;

import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.entity.Result;
import com.epam.horseraces.dao.entity.enums.ResultType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.QuotationDaoImpl;
import com.epam.horseraces.dao.impl.ResultDaoImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code ServiceQuotationImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ServiceQuotationImpl {

    private static final String WIN = "WIN";
    private static final String FIRST_THREE = "FIRST_THREE";
    private static final String UNDERDOG = "UNDERDOG";

    /**
     * @param userId      The user id
     * @param raceId      The race id
     * @param horseId     The horse id
     * @param description The description
     * @param coefficient The coefficent
     * @throws DaoException If query was competed with errors
     */
    public void addQuotation(int userId, int raceId, int horseId, String description, double coefficient) throws DaoException {

        Quotation quotation = new Quotation();
        quotation.setUserId(userId);
        quotation.setRaceId(raceId);
        quotation.setHorseId(horseId);
        quotation.setQuotateDescription(description);
        quotation.setCoefficient(coefficient);

        QuotationDaoImpl dao = new QuotationDaoImpl();
        dao.addRecord(quotation);

    }

    /**
     * @param id The quotation id
     * @throws DaoException If query was competed with errors
     */
    public void deleteQuotation(int id) throws DaoException {

        Quotation quotation = new Quotation();
        quotation.setId(id);

        QuotationDaoImpl dao = new QuotationDaoImpl();
        dao.deleteRecord(quotation);

    }

    /**
     * @param userId      The user id
     * @param raceId      The race id
     * @param horseId     The horse id
     * @param description The description
     * @param coefficient The coefficent
     * @throws DaoException If query was competed with errors
     */
    public void updateQuotation(int id, int userId, int raceId, int horseId, String description, double coefficient) throws DaoException {

        Quotation quotation = new Quotation();
        quotation.setId(id);
        quotation.setUserId(userId);
        quotation.setRaceId(raceId);
        quotation.setHorseId(horseId);
        quotation.setQuotateDescription(description);
        quotation.setCoefficient(coefficient);

        QuotationDaoImpl dao = new QuotationDaoImpl();
        dao.updateRecord(quotation);

    }

    /**
     * @param quotations The list of {@code Quotation} records
     * @return The set of unique races
     */
    public Set<Integer> getRacesNumber(List<Quotation> quotations) {

        Set<Integer> raceSet = new HashSet<Integer>();

        for (Quotation quotation : quotations) {
            Integer raceId = quotation.getRaceId();
            raceSet.add(raceId);
        }

        return raceSet;
    }

    /**
     * @param quotations The list of {@code Quotation} records
     * @param betRaceId  The bet id
     * @return The list of {@code Quotation} records
     */
    public List<Quotation> getUniqueHorsesQuotations(List<Quotation> quotations, Integer betRaceId) {

        List<Quotation> bets = new ArrayList<Quotation>();
        Set<Integer> num = new HashSet<Integer>();
        fillQuotations(quotations, bets, num, betRaceId);

        return bets;
    }

    /**
     * @param quotations The list of {@code Quotation} records
     * @param bets       The list of unique {@code Quotation} records
     * @param set        The integer set
     * @param betRaceId  The bet id
     */
    private void fillQuotations(List<Quotation> quotations, List<Quotation> bets, Set<Integer> set, Integer betRaceId) {

        for (Quotation quotation : quotations) {
            Integer raceId = quotation.getRaceId();
            if (betRaceId.equals(raceId)) {
                addBet(bets, set, quotation);

            }
        }
    }

    /**
     * @param bets      The list of unique {@code Quotation} records
     * @param set       The integer set
     * @param quotation The object of {@code Quotation} class
     */
    private void addBet(List<Quotation> bets, Set<Integer> set, Quotation quotation) {
        Integer horseId = quotation.getHorseId();
        if (set.add(horseId)) {
            bets.add(quotation);
        }
    }

    /**
     * @param description The description
     * @param place       The place
     * @return The boolean value which indicates that quotation is
     * victorious or not
     */
    private boolean isVictorious(String description, Integer place) {

        boolean isVictorious = false;

        if (WIN.equalsIgnoreCase(description)) {
            if (place == 1) {
                isVictorious = true;
            }
        } else if (FIRST_THREE.equalsIgnoreCase(description)) {
            if ((place == 1 || place == 2 || place == 3)) {
                isVictorious = true;
            }
        } else if (UNDERDOG.equalsIgnoreCase(description)) {
            if (place == 4) {
                isVictorious = true;
            }
        } else if (place.equals(Integer.parseInt(description))) {
            isVictorious = true;
        }

        return isVictorious;
    }

    /**
     * @param quotation The list of {@code Quotation} records
     * @throws DaoException If query was competed with errors
     */
    private void changeStatus(Quotation quotation) throws DaoException {

        ResultDaoImpl resultDao = new ResultDaoImpl();
        List<Result> results = resultDao.getAllRecords();

        Integer horseId = quotation.getHorseId();
        Integer raceId = quotation.getRaceId();


        for (Result result : results) {
            Integer resultHorseId = result.getHorseId();
            Integer resultRaceId = result.getRaceId();
            Integer place = result.getPlace();
            if (resultHorseId.equals(horseId) && resultRaceId.equals(raceId)) {
                changeStatusAttribute(quotation, place);
            }
        }
    }

    /**
     * @param quotation The list of {@code Quotation} records
     * @param place     The place
     */
    private void changeStatusAttribute(Quotation quotation, Integer place) {
        String description = quotation.getQuotateDescription();
        ResultType resultType = ResultType.LOSE;
        if (isVictorious(description, place)) {
            resultType = ResultType.WIN;
        }
        quotation.setResultType(resultType);
    }

    /**
     * @throws DaoException If query was competed with errors
     */
    public void changeRaceStatus() throws DaoException {
        QuotationDaoImpl quotationDao = new QuotationDaoImpl();

        List<Quotation> allQuotations = quotationDao.getAllRecords();

        for (Quotation quotation : allQuotations) {
            changeStatus(quotation);
        }

        for (Quotation quotation : allQuotations) {
            quotationDao.changeStatus(quotation);
        }
    }

}
