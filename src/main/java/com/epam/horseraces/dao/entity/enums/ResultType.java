package com.epam.horseraces.dao.entity.enums;

/**
 * The {@code ResultType} describes the type of
 * the existing results
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public enum ResultType {

    TBD(0), WIN(1), LOSE(2);

    /**
     * The identifier of the enum
     */
    private final int id;

    /**
     * The constructor with one parameter
     *
     * @param id The identifier of the enum
     */
    ResultType(int id) {
        this.id = id;
    }

    /**
     * The method the identifier
     *
     * @return The identifier of the enum
     */
    public int getId() {
        return id;
    }

    /**
     * The method the enum using received
     * identifier
     *
     * @param id The identifier of the enum
     * @return The {@code ResultType} object
     */
    public static ResultType getById(int id) {
        ResultType resultType = null;
        for (ResultType object : ResultType.values()) {
            if (object.getId() == id) {
                resultType = object;
            }
        }
        return resultType;
    }
}
