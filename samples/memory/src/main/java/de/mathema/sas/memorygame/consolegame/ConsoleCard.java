package de.mathema.sas.memorygame.consolegame;


import de.mathema.sas.memorygame.cards.Card;
import de.mathema.sas.memorygame.cards.CardView;

import java.util.HashMap;
import java.util.Map;

import static de.mathema.sas.memorygame.cards.Card.Rank.*;
import static de.mathema.sas.memorygame.cards.Card.Suit.*;


public class ConsoleCard implements CardView {
    private Map<Card.Rank, String> ranks = new HashMap<>();
    private Map<Card.Suit, String> suits = new HashMap<>();

    public ConsoleCard() {
        ranks.put(TWO, "2");
        ranks.put(THREE, "3");
        ranks.put(FOUR, "4");
        ranks.put(FIVE, "5");
        ranks.put(SIX, "6");
        ranks.put(SEVEN, "7");
        ranks.put(EIGHT, "8");
        ranks.put(NINE, "9");
        ranks.put(TEN, "10");
        ranks.put(JACK, "J");
        ranks.put(QUEEN, "Q");
        ranks.put(KING, "K");
        ranks.put(ACE, "A");

        suits.put(SPADE, "\u2660");
        suits.put(HEART, "\u2661");
        suits.put(DIAMOND, "\u2662");
        suits.put(CLUB, "\u2663");
    }

    @Override
    public void revealCard(Card.Rank rank, Card.Suit suit) {
        System.out.print(ranks.get(rank) + suits.get(suit));
    }

    @Override
    public void hideCard() {
        System.out.print("?");
    }
}
