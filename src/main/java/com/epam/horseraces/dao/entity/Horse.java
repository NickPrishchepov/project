package com.epam.horseraces.dao.entity;

import java.io.Serializable;

/**
 * The {@code Horse} class that contains the information
 * about horses that participates in horse races
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
public class Horse implements Serializable {

    /**
     * The horse identifier
     */
    private Integer id;

    /**
     * The horse nickname
     */
    private String nickname;

    /**
     * The horse breed
     */
    private String breed;

    /**
     * The horse age
     */
    private Integer age;

    /**
     * The horse jockey name
     */
    private String jockeyName;

    /**
     * The horse rating
     */
    private Double rating;

    /**
     * The methods returns the horse's object string equivalent
     *
     * @return Horses's object string equivalent
     */
    @Override
    public String toString() {
        return "" + id + " " + nickname + " " + breed + " " + age + " " + jockeyName + " " + rating;
    }

    /**
     * The methods sets the horse id
     *
     * @param id The horse identifier
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The methods sets the horse nickname
     *
     * @param nickname The horse nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * The methods sets the horse breed
     *
     * @param breed The horse breed
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * The methods sets the horse age
     *
     * @param age The horse age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * The methods sets the horse jockey name
     *
     * @param jockeyName The horse jockey name
     */
    public void setJockeyName(String jockeyName) {
        this.jockeyName = jockeyName;
    }

    /**
     * The methods sets the horse rating
     *
     * @param rating The horse rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * The methods returns the horse id
     *
     * @return The horse identifier
     */
    public Integer getId() {
        return id;
    }

    /**
     * The methods returns the horse nickname
     *
     * @return The horse nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * The methods returns the horse breed
     *
     * @return The horse breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * The methods returns the horse age
     *
     * @return The horse age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * The methods returns the horse jockey name
     *
     * @return The horse jockey name
     */
    public String getJockeyName() {
        return jockeyName;
    }

    /**
     * The methods returns the horse rating
     *
     * @return The horse rating
     */
    public Double getRating() {
        return rating;
    }
}
