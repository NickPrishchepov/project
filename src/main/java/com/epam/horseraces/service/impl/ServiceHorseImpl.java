package com.epam.horseraces.service.impl;

import com.epam.horseraces.dao.entity.Horse;
import com.epam.horseraces.dao.exception.DaoException;
import com.epam.horseraces.dao.impl.HorseDaoImpl;

/**
 * The {@code ServiceHorseImpl} class provides some methods that
 * install the work with the database source. Also the class includes the methods of
 * getting the appropriate queries
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class ServiceHorseImpl {

    /**
     * @param nickname    Horse nickname
     * @param breed       Horse breed
     * @param horseAge    Horse age
     * @param jockey      Horse jockey name
     * @param horseRating Horse rating
     * @throws DaoException If query was competed with errors
     */
    public void addHorse(String nickname, String breed, int horseAge, String jockey, double horseRating) throws DaoException {

        Horse horse = new Horse();
        horse.setNickname(nickname);
        horse.setBreed(breed);
        horse.setAge(horseAge);
        horse.setJockeyName(jockey);
        horse.setRating(horseRating);

        HorseDaoImpl dao = new HorseDaoImpl();
        dao.addRecord(horse);

    }

    /**
     * @param id Horse id
     * @throws DaoException If the query was competed with errors
     */
    public void deleteHorse(int id) throws DaoException {

        Horse horse = new Horse();
        horse.setId(id);

        HorseDaoImpl dao = new HorseDaoImpl();
        dao.deleteRecord(horse);

    }

    /**
     * @param nickname    Horse nickname
     * @param breed       Horse breed
     * @param horseAge    Horse age
     * @param jockey      Horse jockey name
     * @param horseRating Horse rating
     * @throws DaoException If query was competed with errors
     */
    public void updateHorse(int id, String nickname, String breed, int horseAge, String jockey, double horseRating) throws DaoException {

        Horse horse = new Horse();
        horse.setId(id);
        horse.setNickname(nickname);
        horse.setBreed(breed);
        horse.setAge(horseAge);
        horse.setJockeyName(jockey);
        horse.setRating(horseRating);

        HorseDaoImpl dao = new HorseDaoImpl();
        dao.updateRecord(horse);
    }
}
