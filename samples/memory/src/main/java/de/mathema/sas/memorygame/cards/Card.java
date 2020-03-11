package de.mathema.sas.memorygame.cards;

import java.util.Objects;

public class Card {

    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        JACK, QUEEN, KING, ACE;
    }

    public enum Suit {
        HEART, DIAMOND, CLUB, SPADE;
    }

    private final Rank rank;
    private final Suit suit;

    private boolean faceUp = false;
    private boolean freezedFaceUp = false;

    public static Card of(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public void show(CardView view) {
        if (faceUp || freezedFaceUp) {
            view.revealCard(rank, suit);
        } else {
            view.hideCard();
        }
    }

    public boolean isInPlay() {
        return !freezedFaceUp;
    }

    public void turnFaceUp() {
        this.faceUp = true;
    }

    public void turnFaceDown() {
        this.faceUp = false;
    }

    public void freezeFaceUp() {
        this.freezedFaceUp = true;
    }

    public boolean matches(Card secondCard) {
        return this != secondCard && this.rank == secondCard.rank &&
                this.suit == secondCard.suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
