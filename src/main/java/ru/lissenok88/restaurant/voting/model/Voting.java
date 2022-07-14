package ru.lissenok88.restaurant.voting.model;

import java.time.LocalDateTime;

public class Voting {
    private Integer vote;
    private Integer user_id;
    private Integer restaurant_id;
    private LocalDateTime dateTime;

    public Voting() {

    }

    public Voting(Integer vote, Integer user_id, Integer restaurant_id, LocalDateTime dateTime) {
        this.vote = vote;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.dateTime = dateTime;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
