package de.mathema.sas.uncleanmemory.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Player {

    public static int INITIAL_RATING = 1500;

    @Id
    @GeneratedValue
    Long id;

    private String name;

    @Transient
    private int score = 0;

    private int rating;

    public Player(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name + " (" + rating + "): " + score;
    }

    public String getName() {
        return this.name;
    }

    public boolean outscores(Player otherPlayer) {
        return this.score > otherPlayer.score;
    }

    public void scorePoint() {
        this.score++;
    }

    public void increaseRating() {
        this.rating++;
    }

    public void reduceRating() {
        this.rating--;
    }
}
