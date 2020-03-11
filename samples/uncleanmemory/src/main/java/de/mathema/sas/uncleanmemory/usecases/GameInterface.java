package de.mathema.sas.uncleanmemory.usecases;

import de.mathema.sas.uncleanmemory.entities.Card;
import de.mathema.sas.uncleanmemory.entities.Player;

import java.util.List;

public interface GameInterface {
    List<Card> prepareCardDeck();

    List<Player> createPlayers();

    void selectCards(Card firstCard, Card secondCard, Player player);

    boolean finished();

    void hideCards(Card firstCard, Card secondCard);

    Player nextPlayer(Player player, Card firstCard, Card secondCard);
}
