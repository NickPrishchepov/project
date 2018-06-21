package com.epam.horseraces.dao.entity;

import com.epam.horseraces.dao.entity.enums.Ending;
import com.epam.horseraces.dao.entity.enums.RaceType;

import java.io.Serializable;
import java.util.Date;

/**
 * The {@code Race} class that contains the information
 * about existing races
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class Race implements Serializable {

    /**
     * The race identifier
     */
    private Integer id;

    /**
     * The race date
     */
    private Date date;

    /**
     * The race location
     */
    private String location;

    /**
     * The race type
     */
    private String type;

    /**
     * The race status
     */
    private Ending ending;

    /**
     * The race type
     */
    private RaceType raceType;

    /**
     * The race status
     */
    private boolean isFinished;

    /**
     * The method returns the race type
     *
     * @return The race type
     */
    public RaceType getRaceType() {
        return raceType;
    }

    /**
     * The method sets the race type
     *
     * @param raceType The race type
     */
    public void setRaceType(RaceType raceType) {
        this.raceType = raceType;
    }

    /**
     * The method returns the race status
     *
     * @return The race status
     */
    public Ending getEnding() {
        return ending;
    }

    /**
     * The method returns the race ending
     *
     * @param ending The race status
     */
    public void setEnding(Ending ending) {
        this.ending = ending;
    }

    /**
     * The method returns the race status
     *
     * @return The race status
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * The method returns the race ending
     *
     * @param finished The race status
     */
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    /**
     * The method sets the race id
     *
     * @param id The race id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The method sets the race date
     *
     * @param date The race date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * The method sets the race location
     *
     * @param location The race location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The method sets the race type
     *
     * @param type The race type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The method returns the race id
     *
     * @return The race id
     */
    public Integer getId() {
        return id;
    }

    /**
     * The method returns the race date
     *
     * @return The race date
     */
    public Date getDate() {
        return date;
    }

    /**
     * The method returns the race location
     *
     * @return The race location
     */
    public String getLocation() {
        return location;
    }

    /**
     * The method returns the race type
     *
     * @return The race type
     */
    public String getType() {
        return type;
    }

    /**
     * The method makes comparison of two objects
     * and returns the result which is presented in
     * boolean form
     *
     * @param obj The {@code Race} object that would be compared
     *            with existing {@code Race} object
     * @return The boolean result of comparison
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Race race = (Race) obj;
        return race.getDate().equals(date) && race.getLocation().equals(location) && race.getType().equals(type);
    }

    /**
     * The method returns the race's object string equivalent
     *
     * @return The {@code Race} object's parameters string
     * equivalent
     */
    @Override
    public String toString() {
        return "" + id + date + location + type;
    }
}
