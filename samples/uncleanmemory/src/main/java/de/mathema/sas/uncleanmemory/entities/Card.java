package de.mathema.sas.uncleanmemory.entities;


public class Card {
    private final Rank rank;
    private final Suit suit;
    private boolean hidden = true;
    private boolean inPlay = true;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        if(hidden) {
            return "?";
        }

        return "" + rank + suit;
    }

    public boolean inPlay() {
        return this.inPlay;
    }

    public void showFaceUp() {
        this.hidden = false;
    }

    public void showFaceDown() {
        this.hidden = true;
    }

    public boolean matches(Card secondCard) {
        return this != secondCard && this.rank == secondCard.rank &&
                this.suit == secondCard.suit;
    }

    public void removeFromPlay() {
        this.inPlay = false;
    }
}
