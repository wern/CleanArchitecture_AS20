package de.mathema.sas.memorygame.rules;

import de.mathema.sas.memorygame.cards.Card;
import de.mathema.sas.memorygame.player.Player;

import java.util.List;

public interface GameView {
    void update(List<Card> cards, List<Player> players);

    Card selectCard(Player player, List<Card> cards);

    void winner(Player player);
}
