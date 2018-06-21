package com.epam.horseraces.dao.entity;

import com.epam.horseraces.dao.entity.enums.ResultType;

import java.io.Serializable;


/**
 * The {@code Quotation} class that contains the information
 * about quotations on the existing horses of the horse races
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class Quotation implements Serializable {

    /**
     * The quotation identifier
     */
    private Integer id;

    /**
     * The user identifier
     */
    private Integer userId;

    /**
     * The race identifier
     */
    private Integer raceId;

    /**
     * The horse identifier
     */
    private Integer horseId;

    /**
     * The quotation description
     */
    private String quotateDescription;

    /**
     * The quotation coefficient
     */
    private Double coefficient;

    /**
     * The result type
     */
    private ResultType resultType;

    /**
     * The methods returns the result type
     *
     * @return The result type
     */
    public ResultType getResultType() {
        return resultType;
    }

    /**
     * The methods sets the result type
     *
     * @param resultType The result type
     */
    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    /**
     * The methods sets the quotation id
     *
     * @param id The quotation identifier
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The methods sets the user id
     *
     * @param userId The user identifier
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * The methods sets the race id
     *
     * @param raceId The race identifier
     */
    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    /**
     * The methods sets the horse id
     *
     * @param horseId The horse identifier
     */
    public void setHorseId(Integer horseId) {
        this.horseId = horseId;
    }

    /**
     * The methods sets the quotation description
     *
     * @param quotateDescription The quotation description
     */
    public void setQuotateDescription(String quotateDescription) {
        this.quotateDescription = quotateDescription;
    }

    /**
     * The methods sets the quotation coefficient
     *
     * @param coefficient The quotation coefficient
     */
    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * The methods returns the quotation id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * The methods returns the user id
     *
     * @return The user identifier
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * The methods returns the race id
     *
     * @return The race identifier
     */
    public Integer getRaceId() {
        return raceId;
    }

    /**
     * The methods returns the horse id
     *
     * @return The horse identifier
     */
    public Integer getHorseId() {
        return horseId;
    }

    /**
     * The methods returns the quotation description
     *
     * @return The quotation description
     */
    public String getQuotateDescription() {
        return quotateDescription;
    }

    /**
     * The methods returns the quotation coefficient
     *
     * @return The quotation coefficient
     */
    public Double getCoefficient() {
        return coefficient;
    }

    /**
     * The methods returns the quotation's object string equivalent
     *
     * @return The {@code Quotation} object's attributes string
     * equivalent
     */
    @Override
    public String toString() {
        return "" + id + " " + userId + " " + raceId + " " + horseId + " " + quotateDescription + " " + coefficient;
    }
}
