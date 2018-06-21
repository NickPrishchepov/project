package com.epam.horseraces.dao.entity.enums;

/**
 * The {@code RaceType} describes the type of
 * the existing races
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public enum RaceType {

    FLAT_RACING(1), STEEPLECHASE(2), HARNESS_RACING(3);

    /**
     * The identifier of the enum
     */
    private final int id;

    /**
     * The constructor with one parameter
     *
     * @param id The identifier of the enum
     */
    RaceType(int id) {
        this.id = id;
    }

    /**
     * The method returns enum's identifier
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
     * @return The {@code RaceType} object
     */
    public static RaceType getById(int id) {
        RaceType raceType = null;
        for (RaceType object : RaceType.values()) {
            if (object.getId() == id) {
                raceType = object;
            }
        }
        return raceType;
    }

    /**
     * @param name The name of the enum which is presented
     *             in text form
     * @return The {@code RaceType} object
     */
    public static RaceType getByName(String name) {
        RaceType raceType = null;
        for (RaceType object : RaceType.values()) {
            String enumName = object.name();
            if (enumName.equalsIgnoreCase(name)) {
                raceType = object;
            }
        }
        return raceType;
    }

}
