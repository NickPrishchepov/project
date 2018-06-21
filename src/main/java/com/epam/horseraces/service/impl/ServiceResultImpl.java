package com.epam.horseraces.service.impl;

import com.epam.horseraces.dao.entity.Quotation;
import com.epam.horseraces.dao.entity.Result;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.QuotationDaoImpl;
import com.epam.horseraces.dao.impl.ResultDaoImpl;

import java.util.*;

/**
 * The {@code ServiceResultImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ServiceResultImpl {

    private static final Integer[] FIRST_THREE = {1, 2, 3};
    private static final Integer WIN = 1;
    private static final Integer UNDERDOG = 5;
    private static final String GENERATING_RESULTS_ERROR = "The results wasn't generated sucessfully. All the races should have at least two horses.";

    /**
     * @throws DaoException If the query was competed with errors
     */
    public void generateResults() throws DaoException {

        QuotationDaoImpl quotationDao = new QuotationDaoImpl();
        List<Quotation> quotations = quotationDao.getAllRecords();

        ServiceQuotationImpl serviceQuotation = new ServiceQuotationImpl();

        Set<Integer> racesNumber = serviceQuotation.getRacesNumber(quotations);
        Iterator<Integer> iterator = racesNumber.iterator();

        Integer size = racesNumber.size();

        for (int counter = 0; counter < size; counter++) {
            Integer raceId = iterator.next();
            List<Quotation> horsesNumber = serviceQuotation.getUniqueHorsesQuotations(quotations, raceId);
            System.out.print(horsesNumber.size() + ": is size");
            if (horsesNumber.size() < 4) {
                throw new DaoException(GENERATING_RESULTS_ERROR);
            }
            List<Integer> places = getPlaces(horsesNumber);
            List<Result> results = makeResults(horsesNumber, places);
            ResultDaoImpl resultDao = new ResultDaoImpl();
            for (Result result : results) {
                resultDao.addRecord(result);
            }
        }
    }


    /**
     * @param quotations The list of {@code Quotation} records
     * @param places     The list of places
     * @return The list of {@code Result} class
     */
    private List<Result> makeResults(List<Quotation> quotations, List<Integer> places) {

        List<Result> results = new ArrayList<Result>();

        for (int counter = 0; counter < quotations.size(); counter++) {
            Integer horseId = quotations.get(counter).getHorseId();
            Integer raceId = quotations.get(counter).getRaceId();
            Integer place = places.get(counter);
            Result result = new Result();
            result.setHorseId(horseId);
            result.setRaceId(raceId);
            result.setPlace(place);

            results.add(result);
        }

        return results;
    }


    /**
     * @param quotations The list of {@code Quotation} class
     * @return The list of generated places
     */
    private List<Integer> getPlaces(List<Quotation> quotations) {

        Set<Integer> set = new HashSet<Integer>();
        List<Integer> places = new ArrayList<Integer>();
        for (int counter = 0; counter < quotations.size(); counter++) {
            generatePlace(set, quotations.size(), places);
        }
        return places;
    }

    /**
     * @param set    The race set
     * @param size   The unique horse quotation's size
     * @param places The list of places
     */
    private void generatePlace(Set<Integer> set, Integer size, List<Integer> places) {
        int random = 1 + (int) (Math.random() * ((size - 1) + 1));
        if (set.add(random)) {
            places.add(random);
        } else {
            generatePlace(set, size, places);
        }

    }

}
