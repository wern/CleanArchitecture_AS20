package de.mathema.sas.uncleanmemory.usecases;

import de.mathema.sas.uncleanmemory.entities.Card;
import de.mathema.sas.uncleanmemory.entities.Player;
import de.mathema.sas.uncleanmemory.entities.Rank;
import de.mathema.sas.uncleanmemory.entities.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryGame implements GameInterface {

    private Player firstPlayer;
    private Player secondPlayer;
    private List<Card> cards;

    public MemoryGame() {
        init();
    }

    private final void init() {
        this.firstPlayer = new Player("Player 1", Player.INITIAL_RATING);
        this.secondPlayer = new Player("Player 2", Player.INITIAL_RATING);
    }

    public List<Card> prepareCardDeck() {
        this.cards = new ArrayList<>();

        for (Rank rank : Rank.values()) {
            this.cards.add(new Card(rank, Suit.SPADE));
            this.cards.add(new Card(rank, Suit.SPADE));
        }

        Collections.shuffle(this.cards);

        return Collections.unmodifiableList(this.cards);
    }

    public List<Player> createPlayers() {
        return Arrays.asList(firstPlayer, secondPlayer);
    }

    @Override
    public void selectCards(Card firstCard, Card secondCard, Player player) {
        firstCard.showFaceUp();
        secondCard.showFaceUp();

        if (firstCard.matches(secondCard)) {
            player.scorePoint();
            firstCard.removeFromPlay();
            secondCard.removeFromPlay();
        }
    }

    @Override
    public boolean finished() {
        return this.cards.stream().noneMatch(Card::inPlay);
    }

    @Override
    public void hideCards(Card firstCard, Card secondCard) {
        if (firstCard.inPlay()) {
            firstCard.showFaceDown();
        }

        if (secondCard.inPlay()) {
            secondCard.showFaceDown();
        }
    }

    @Override
    public Player nextPlayer(Player player, Card firstCard, Card secondCard) {
        if (firstCard.matches(secondCard)) {
            return player;
        }

        if (player.equals(firstPlayer)) {
            return secondPlayer;
        }

        return firstPlayer;
    }
}
