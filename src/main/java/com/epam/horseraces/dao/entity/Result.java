package com.epam.horseraces.dao.entity;

import java.io.Serializable;

/**
 * The {@code Result} class that contains the information
 * about the results of the finished races
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class Result implements Serializable {

    /**
     * The result identifier
     */
    private Integer id;

    /**
     * The horse identifier
     */
    private Integer horseId;

    /**
     * The race identifier
     */
    private Integer raceId;

    /**
     * The place
     */
    private Integer place;

    /**
     * The method returns the result id
     *
     * @return The result id
     */
    public Integer getId() {
        return id;
    }

    /**
     * The method sets the result id
     *
     * @param id The result identifier
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The method sets the horse id
     *
     * @param horseId The horse identifier
     */
    public void setHorseId(Integer horseId) {
        this.horseId = horseId;
    }

    /**
     * The method sets the race id
     *
     * @param raceId The race identifier
     */
    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    /**
     * The method sets the place
     *
     * @param place The place
     */
    public void setPlace(Integer place) {
        this.place = place;
    }

    /**
     * The method returns the horse id
     *
     * @return The horse identifier
     */
    public Integer getHorseId() {
        return horseId;
    }

    /**
     * The method returns the race id
     *
     * @return The race identifier
     */
    public Integer getRaceId() {
        return raceId;
    }

    /**
     * The method returns the place
     *
     * @return The place identifier
     */
    public Integer getPlace() {
        return place;
    }

    /**
     * The method returns the result's object string equivalent
     *
     * @return The {@code Result} object's parameters string
     * equivalent
     */
    @Override
    public String toString() {
        return "" + id + " " + horseId + " " + raceId + " " + place;
    }
}

