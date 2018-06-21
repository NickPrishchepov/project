package com.epam.horseraces.dao.entity.enums;

/**
 * The {@code Ending} describes the status of
 * the existing races
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public enum Ending {

    NOT_ENDED(0), ENDED(1);

    /**
     * The identifier of the enum
     */
    private final int id;

    /**
     * The constructor with one parameter
     *
     * @param id The identifier of the enum
     */
    Ending(int id) {
        this.id = id;
    }

    /**
     * The method returns the enum's identifier
     *
     * @return The identifier of the enum
     */
    public int getId() {
        return id;
    }

    /**
     * The method returns the enum using the
     * received identifier
     *
     * @param id The identifier of the enum
     * @return The {@code Ending} object
     */
    public static Ending getById(int id) {
        Ending ending = null;
        for (Ending object : Ending.values()) {
            if (object.getId() == id) {
                ending = object;
            }
        }
        return ending;
    }
}
