package de.mathema.sas.memorygame.consolegame;

import de.mathema.sas.memorygame.cards.CardView;
import de.mathema.sas.memorygame.player.Player;
import de.mathema.sas.memorygame.player.PlayerView;
import de.mathema.sas.memorygame.rules.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsoleGameConfiguration {

    public Game createMemoryGame(Player firstPlayer, Player secondPlayer) {
        return new Game(createConsoleUI(), firstPlayer, secondPlayer);
    }

    @Bean
    public ConsoleUI createConsoleUI() {
        return new ConsoleUI(createPlayerView(), createCardView());
    }

    @Bean
    public PlayerView createPlayerView() {
        return new ConsolePlayer();
    }

    @Bean
    public CardView createCardView() {
        return new ConsoleCard();
    }
}
