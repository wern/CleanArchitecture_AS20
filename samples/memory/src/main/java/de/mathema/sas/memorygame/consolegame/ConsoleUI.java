package de.mathema.sas.memorygame.consolegame;

import de.mathema.sas.memorygame.cards.Card;
import de.mathema.sas.memorygame.cards.CardView;
import de.mathema.sas.memorygame.player.Player;
import de.mathema.sas.memorygame.player.PlayerView;
import de.mathema.sas.memorygame.rules.GameView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements GameView {

    private final PlayerView playerView;
    private final CardView cardView;

    public ConsoleUI(PlayerView playerView, CardView cardView) {
        this.playerView = playerView;
        this.cardView = cardView;
    }

    @Override
    public void update(List<Card> cards, List<Player> players) {
        clearScreen();
        players.stream().forEach(player -> player.showInfo(playerView));
        print(cards);
    }

    @Override
    public Card selectCard(Player player, List<Card> cards) {
        System.out.println();
        player.showName(playerView);
        System.out.print(" Select card: ");

        Scanner in = new Scanner(System.in);
        int cardPosition = in.nextInt();

        return cards.get(cardPosition - 1);
    }

    @Override
    public void winner(Player player) {
        System.out.println("\nWinner: ");
        player.showInfo(playerView);
    }

    private void print(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            if (i % 4 == 0) {
                System.out.println();
            }

            System.out.print(i + 1 + ":\t");
            cards.get(i).show(cardView);
            System.out.print("\t");
        }
    }

    private void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
