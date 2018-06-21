package com.epam.horseraces.service.impl;

import com.epam.horseraces.dao.entity.Race;
import com.epam.horseraces.dao.entity.enums.Ending;
import com.epam.horseraces.dao.entity.enums.RaceType;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.RaceDaoImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The {@code ServiceRaceImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ServiceRaceImpl {

    private static final String EMPTY_STRING = "";

    /**
     * @param date     The date
     * @param location The location
     * @param type     The type
     * @throws DaoException If the query was competed with errors
     */
    public void addRace(Date date, String location, String type) throws DaoException {


        Race race = new Race();
        race.setDate(date);
        race.setLocation(location);
        RaceType raceType = RaceType.getByName(type);
        race.setRaceType(raceType);

        RaceDaoImpl dao = new RaceDaoImpl();
        dao.addRecord(race);

    }

    /**
     * @param id The race id
     * @throws DaoException If the query was competed with errors
     */
    public void deleteRace(int id) throws DaoException {

        Race race = new Race();
        race.setId(id);

        RaceDaoImpl dao = new RaceDaoImpl();
        dao.deleteRecord(race);

    }

    /**
     * @param id       The race id
     * @param date     The date
     * @param location The location
     * @param type     The type
     * @throws DaoException If the query was competed with errors
     */
    public void editRace(int id, Date date, String location, String type) throws DaoException {

        Race race = new Race();
        race.setId(id);
        race.setDate(date);
        race.setLocation(location);
        RaceType raceType = RaceType.getByName(type);
        race.setRaceType(raceType);


        RaceDaoImpl dao = new RaceDaoImpl();
        dao.updateRecord(race);

    }

    /**
     * @param stringDate The date which is presented in text form
     * @return The sql format date
     */
    public Date getSqlDateFormat(String stringDate) {
        if (stringDate.equals(EMPTY_STRING)) {
            return null;
        }
        Date date = null;
        String sql_format_date = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(sql_format_date, Locale.ENGLISH);
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param isFinished The parameter that indicates the ending
     *                   of the race
     * @param id         The race id
     * @throws DaoException If the query was competed with errors
     */
    public void changeStatus(Boolean isFinished, Integer id) throws DaoException {

        Race race = new Race();
        race.setId(id);
        race.setFinished(isFinished);

        RaceDaoImpl dao = new RaceDaoImpl();
        dao.changeStatus(race);
    }

    /**
     * @throws DaoException If the query was competed with errors
     */
    public void changeRaceStatus() throws DaoException {
        RaceDaoImpl dao = new RaceDaoImpl();
        List<Race> races = dao.getAllRecords();
        for (Race race : races) {
            Ending ending = Ending.ENDED;
            race.setEnding(ending);
            dao.changeStatus(race);
        }
    }
}
