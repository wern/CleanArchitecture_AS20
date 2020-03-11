package de.mathema.sas.memorygame.cards;

public interface CardView {
    void revealCard(Card.Rank rank, Card.Suit suit);

    void hideCard();
}
