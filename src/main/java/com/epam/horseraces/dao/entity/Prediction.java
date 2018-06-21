package com.epam.horseraces.dao.entity;

import com.epam.horseraces.dao.entity.enums.ResultType;

import java.io.Serializable;

/**
 * The {@code Prediction} class that contains the information
 * about predictions on the existing horses of the horse races
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class Prediction implements Serializable {

    /**
     * The prediction identifier
     */
    private Integer id;

    /**
     * The user identifier
     */
    private Integer userId;

    /**
     * The quotation identifier
     */
    private Integer quotationId;

    /**
     * The amount of money
     */
    private Double money;

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
     * The methods sets the prediction id
     *
     * @param id The indentifier of the result type
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The methods returns the user id
     *
     * @return The prediction identifier
     */
    public Integer getUserId() {
        return userId;
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
     * The methods sets the quotation id
     *
     * @param quotationId The quotation identifier
     */
    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    /**
     * The methods sets the amount of money
     *
     * @param money The amount of money
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * The methods returns the prediction id
     *
     * @return The prediction identifier
     */
    public Integer getId() {
        return id;
    }

    /*
    The methods returns the quotation id

     * @return
     *        The quotation identifier
     */
    public Integer getQuotationId() {
        return quotationId;
    }

    /**
     * The methods returns the amount of money
     *
     * @return The amount of money
     */
    public Double getMoney() {
        return money;
    }
}
