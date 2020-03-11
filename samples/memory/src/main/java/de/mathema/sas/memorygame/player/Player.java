package de.mathema.sas.memorygame.player;

import java.util.Objects;

public class Player {

    private final PlayerStorage playerStorage;

    public static final int INITIAL_RATING = 1500;

    private final String name;
    private int rating;
    private int score;

    public Player(String name, int rating, PlayerStorage playerStorage) {
        this.name = name;
        this.rating = rating;
        this.playerStorage = playerStorage;
    }

    public void showInfo(PlayerView view) {
        view.showInfo(this.name, this.rating, this.score);
    }

    public void showName(PlayerView view) {
        view.showName(this.name);
    }

    public void scorePoint() {
        this.score++;
    }

    public boolean outscores(Player otherPlayer) {
        return this.score > otherPlayer.score;
    }

    public void increaseRating() {
        this.rating++;
        playerStorage.save(rating);
    }

    public void reduceRating() {
        this.rating--;
        playerStorage.save(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return rating == player.rating &&
                score == player.score &&
                name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rating, score);
    }
}
